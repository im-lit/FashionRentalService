package com.example.fashionrentalservice.model.response;

import com.example.fashionrentalservice.model.dto.product.CategoryDTO;
import com.example.fashionrentalservice.model.dto.product.ProductDTO;
import com.example.fashionrentalservice.model.dto.product.ProductRentalPricesDTO;
import com.example.fashionrentalservice.model.dto.product.RequestAddingProductDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseEntity {
	private int productID;

	private String productName;
	
	private String productReceiptUrl;
	
	private String productAvt;
	
	private String productCondition;

	private String description;

	private double price;

	private String status;
	
	private String checkType;

	private CategoryDTO category;
	
	private int productOwnerID;
	
	private ProductRentalPricesDTO productRentalPricesDTO;
	

	
	private String productSpecificationData;
	
	

	public static ProductResponseEntity fromProductDTO(ProductDTO dto) {
		return ProductResponseEntity.builder()
				.productID(dto.getProductID())
				.productName(dto.getProductName())
				.productCondition(dto.getProductCondition())
				.productAvt(dto.getProductAvt())
				.productReceiptUrl(dto.getProductReceiptUrl())
				.description(dto.getDescription())
				.price(dto.getPrice())
				.status(dto.getStatus().toString())
				.checkType(dto.getCheckType().toString())
				.category(dto.getCategory())
				.productOwnerID(dto.getProductownerDTO().getProductownerID())
				.productSpecificationData(dto.getProductSpecificationData())
				.productRentalPricesDTO(dto.getProductRentalPricesDTO())
				.build();
	}
}
