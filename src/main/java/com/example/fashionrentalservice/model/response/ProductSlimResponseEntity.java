package com.example.fashionrentalservice.model.response;

import com.example.fashionrentalservice.model.dto.product.ProductDTO;
import com.example.fashionrentalservice.model.dto.product.ProductRentalPricesDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSlimResponseEntity {
	private int productID;

	private String productName;
	
	private String productCondition;
	
	private String productAvt;
	
	private double price;

	private String checkType;
	
	private String productOwnerName;
	
	private String productSpecifications;
	
	private ProductRentalPricesDTO productRentalPricesDTO;
	
	
	public static ProductSlimResponseEntity fromProductDTO(ProductDTO dto) {
		return ProductSlimResponseEntity.builder()
				.productID(dto.getProductID())
				.productName(dto.getProductName())
				.productCondition(dto.getProductCondition())
				.productAvt(dto.getProductAvt())
				.price(dto.getPrice())
				.checkType(dto.getCheckType().toString())
				.productOwnerName(dto.getProductownerDTO().getFullName())
				.productSpecifications(dto.getProductSpecificationData())
				.productRentalPricesDTO(dto.getProductRentalPricesDTO())
				.build();
	}
}
