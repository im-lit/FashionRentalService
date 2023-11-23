package com.example.fashionrentalservice.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fashionrentalservice.exception.PendingMoneyNegative;
import com.example.fashionrentalservice.exception.StaffNotFoundByID;
import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.dto.account.CustomerDTO;
import com.example.fashionrentalservice.model.dto.product.FavoriteProductDTO;
import com.example.fashionrentalservice.model.dto.product.FavoriteProductDTO.FavoriteStatus;
import com.example.fashionrentalservice.model.dto.product.ProductDTO;
import com.example.fashionrentalservice.model.dto.product.VoucherDTO;
import com.example.fashionrentalservice.model.request.FavoriteProductRequestEntity;
import com.example.fashionrentalservice.model.response.FavoriteProductResponseEntity;
import com.example.fashionrentalservice.model.response.VoucherResponseEntity;
import com.example.fashionrentalservice.repositories.CustomerRepository;
import com.example.fashionrentalservice.repositories.FavoriteProductRepository;
import com.example.fashionrentalservice.repositories.ProductRepository;

@Service
public class FavoriteProductService {
	@Autowired
	private FavoriteProductRepository fpRepo;
	
	@Autowired
	private ProductRepository productRepo;
	@Autowired
	private CustomerRepository cusRepo;
	
	public List<FavoriteProductResponseEntity> getAllVoucher() {
		return 	fpRepo.findAll().stream()
                .map(FavoriteProductResponseEntity::fromFavoriteProductDTO)
                .collect(Collectors.toList());
	}
	//================================== List All FavoriteProduct ========================================
	public List<FavoriteProductResponseEntity> getFavoriteProductByCusID(int customerID) throws  CrudException {
		List<FavoriteProductDTO> dto = fpRepo.findFavoriteByCusID(customerID);
		
		if(dto==null) {
			throw new StaffNotFoundByID();
		}	
		return FavoriteProductResponseEntity.fromListFavoriteProductDTO(dto);
	}
	
	
	
	public FavoriteProductResponseEntity createFavoriteProduct(FavoriteProductRequestEntity entity) throws CrudException {
		FavoriteProductDTO checked = fpRepo.findByCustomerAndProduct(entity.getCustomerID(), entity.getProductID());
	
		FavoriteProductDTO dto = FavoriteProductDTO.builder()
				.customerDTO(cusRepo.findById(entity.getCustomerID()).orElse(null))
				.productDTO(productRepo.findById(entity.getProductID()).orElse(null))
				.status(FavoriteStatus.ACTIVE)
				.build();
		
	if(checked!=null) {
			dto.setStatus(FavoriteStatus.ACTIVE);
		}
		
		CustomerDTO cusCheck=cusRepo.findById(entity.getCustomerID()).orElse(null);
		ProductDTO productCheck=productRepo.findById(entity.getProductID()).orElse(null);
		if(cusCheck==null) {
			throw new PendingMoneyNegative("Can't find CustomerID to add Favorite");
		}else if(productCheck==null) {
			throw new PendingMoneyNegative("Can't find CustomerID to add Favorite");
		}
		return FavoriteProductResponseEntity.fromFavoriteProductDTO(fpRepo.save(dto));
	}
	
	public FavoriteProductResponseEntity unmarkFavoriteByFavoriteID(int favoriteproductID) throws CrudException {
		FavoriteProductDTO dto = fpRepo.findById(favoriteproductID).orElseThrow();
		if(dto==null) {
			throw new PendingMoneyNegative("Can not find favorite Product by ID");
		}
		dto.setStatus(FavoriteStatus.INACTIVE);
		
		return FavoriteProductResponseEntity.fromFavoriteProductDTO(fpRepo.save(dto));
	}
	public FavoriteProductResponseEntity markFavoriteByFavoriteID(int favoriteproductID) throws CrudException {
		FavoriteProductDTO dto = fpRepo.findById(favoriteproductID).orElseThrow();
		if(dto==null) {
			throw new PendingMoneyNegative("Can not find favorite Product by ID");
		}
		dto.setStatus(FavoriteStatus.ACTIVE);
		
		return FavoriteProductResponseEntity.fromFavoriteProductDTO(fpRepo.save(dto));
	}
	
	
}
