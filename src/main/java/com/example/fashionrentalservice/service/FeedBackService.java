package com.example.fashionrentalservice.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.dto.account.CustomerDTO;
import com.example.fashionrentalservice.model.dto.product.FavoriteProductDTO;
import com.example.fashionrentalservice.model.dto.product.FeedBackDTO;
import com.example.fashionrentalservice.model.dto.product.ProductDTO;
import com.example.fashionrentalservice.model.dto.product.FavoriteProductDTO.FavoriteStatus;
import com.example.fashionrentalservice.model.request.FavoriteProductRequestEntity;
import com.example.fashionrentalservice.model.request.FeedBackRequestEntity;
import com.example.fashionrentalservice.model.response.FavoriteProductResponseEntity;
import com.example.fashionrentalservice.model.response.FeedBackResponseEntity;
import com.example.fashionrentalservice.repositories.CustomerRepository;

import com.example.fashionrentalservice.repositories.FeedBackRepository;
import com.example.fashionrentalservice.repositories.ProductRepository;
import com.example.fashionrentalservice.exception.PendingMoneyNegative;



@Service
public class FeedBackService {
	@Autowired
	private FeedBackRepository fbRepo;
	@Autowired
	private ProductRepository productRepo;
	@Autowired
	private CustomerRepository cusRepo;
	
	
	public List<FeedBackResponseEntity> getFeedBackByProductID(int productID) throws  CrudException {
		List<FeedBackDTO> dto = fbRepo.findFeedBackByProductID(productID);
		
		if(dto==null) {
			throw new PendingMoneyNegative("Can't find CustomerID to add Favorite");
		}	
		return FeedBackResponseEntity.fromListFeedBackDTO(dto);
	}
	

	public FeedBackResponseEntity createFeedBackProduct(FeedBackRequestEntity entity) throws CrudException {
		CustomerDTO cusCheck=cusRepo.findById(entity.getCustomerID()).orElse(null);
		ProductDTO productCheck=productRepo.findById(entity.getProductID()).orElse(null);
		String description;
		if(cusCheck==null) {
		throw new PendingMoneyNegative("Can't find CustomerID to create FeedBack");
	}else if(productCheck==null) {
			throw new PendingMoneyNegative("Can't find CustomerID to add Favorite");
		}
		FeedBackDTO checked= fbRepo.findFeedBackByCustomerAndProductID(entity.getCustomerID(), entity.getProductID());
		if(checked!=null) {
			throw new PendingMoneyNegative("This customer already feedback this product");
		}
		FeedBackDTO dto = FeedBackDTO.builder()
				.createdtDate(LocalDate.now())
				.customerDTO(cusRepo.findById(entity.getCustomerID()).orElse(null))
				.productDTO(productRepo.findById(entity.getProductID()).orElse(null))
				.description(entity.getDescription())
				.build();

//		
//		FavoriteProductDTO checked = fpRepo.findByCustomerAndProduct(entity.getCustomerID(),entity.getProductID());
//		if(checked!=null) {
//			int favoriteFound = fpRepo.findFavoriteProductIDByCustomerAndProduct(entity.getCustomerID(),entity.getProductID());
//			
//			return markFavoriteByFavoriteID(favoriteFound);
////			throw new PendingMoneyNegative("Can't find CustomerID to add Favorite");
//		}
//		FavoriteProductDTO dto = FavoriteProductDTO.builder()
//				.customerDTO(cusRepo.findById(entity.getCustomerID()).orElse(null))
//				.productDTO(productRepo.findById(entity.getProductID()).orElse(null))
//				.status(FavoriteStatus.ACTIVE)
//				.build();
		 return FeedBackResponseEntity.fromFeedBackDTO(fbRepo.save(dto));
	}
	
	
}
