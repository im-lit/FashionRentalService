package com.example.fashionrentalservice.service;

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
	
	
    public ProductRentalPricesResponseEntity createRentalPrices(ProductRentalPricesRequestEntity entity) {
    	ProductRentalPricesDTO dto = ProductRentalPricesDTO.builder()
                .rentPrice1(entity.getRentPrice1())
                .rentPrice4(entity.getRentPrice4())
                .rentPrice7(entity.getRentPrice7())
                .rentPrice10(entity.getRentPrice10())
                .rentPrice14(entity.getRentPrice14())
                .productDTO(productRepo.findById(entity.getProductID()).orElseThrow())
                .build();
    	

        return ProductRentalPricesResponseEntity.fromProductRentalPricesDTO(rentalPriceRepo.save(dto));
    }
    
    
    public ProductRentalPricesResponseEntity deleteRentalPrices(int id) {
    	ProductRentalPricesDTO dto = rentalPriceRepo.findById(id).orElseThrow();
    	rentalPriceRepo.deleteById(id);

        return ProductRentalPricesResponseEntity.fromProductRentalPricesDTO(dto);
    }
}
