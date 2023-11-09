package com.example.fashionrentalservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fashionrentalservice.model.dto.product.ProductRentalPricesDTO;
import com.example.fashionrentalservice.model.request.ProductRentalPricesRequestEntity;
import com.example.fashionrentalservice.model.response.ProductRentalPricesResponseEntity;
import com.example.fashionrentalservice.repositories.ProductRentalPricesRepository;
import com.example.fashionrentalservice.repositories.ProductRepository;
@Service
public class ProductRentalPricesService {
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private ProductRentalPricesRepository rentalPriceRepo;
	
	
    public List<ProductRentalPricesResponseEntity> createRentalPrices(ProductRentalPricesRequestEntity entity) {
    	List<ProductRentalPricesDTO> list = new ArrayList<>();
    		  	 for (int i = 0; i < entity.getMockDay().size(); i++) {
    	        ProductRentalPricesDTO rentPrice = new ProductRentalPricesDTO();
    	        rentPrice.setMockDay(entity.getMockDay().get(i));
    	        rentPrice.setProductDTO(productRepo.findById(entity.getProductID()).orElseThrow());
    	        rentPrice.setRentPrice(entity.getRentPrice().get(i));
    	        list.add(rentPrice);
    		  	 }
    		  	 
        return ProductRentalPricesResponseEntity.fromListProductRentalPricesDTO(rentalPriceRepo.saveAll(list));
    }
    
    public List<ProductRentalPricesResponseEntity> getAllRentPricesByProductID(int productID) { 	
    	return  rentalPriceRepo.findAllByProductID(productID).stream()
                .map(ProductRentalPricesResponseEntity::fromProductRentalPricesDTO)
                .collect(Collectors.toList());  
    	}
    
    
    public ProductRentalPricesResponseEntity deleteRentalPrices(int id) {
    	ProductRentalPricesDTO dto = rentalPriceRepo.findById(id).orElseThrow();
    	rentalPriceRepo.deleteById(id);

        return ProductRentalPricesResponseEntity.fromProductRentalPricesDTO(dto);
    }
}
