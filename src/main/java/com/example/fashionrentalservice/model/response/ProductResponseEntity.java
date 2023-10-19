package com.example.fashionrentalservice.model.response;

import com.example.fashionrentalservice.model.dto.account.ProductOwnerDTO;
import com.example.fashionrentalservice.model.dto.product.CategoryDTO;
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
public class ProductResponseEntity {
	private int productID;

	private String productName;
	
	private String productReceiptUrl;

	private String description;

	private double price;

	private String status;

	private boolean forSale;

	private boolean forRent;

	private CategoryDTO category;
	
	private int productOwnerID;
	
	private ProductRentalPricesDTO productRentalPricesDTO;
	
	private String productSpecificationData;
	
	

	public static ProductResponseEntity fromProductDTO(ProductDTO dto) {
		return ProductResponseEntity.builder()
				.productID(dto.getProductID())
				.productName(dto.getProductName())
				.productReceiptUrl(dto.getProductReceiptUrl())
				.description(dto.getDescription())
				.price(dto.getPrice())
				.status(dto.getStatus().toString())
				.forRent(dto.isForRent())
				.forSale(dto.isForSale())
				.category(dto.getCategory())
				.productOwnerID(dto.getProductownerDTO().getProductownerID())
				.productSpecificationData(dto.getProductSpecificationData())
				.productRentalPricesDTO(dto.getProductRentalPricesDTO())
				.build();
	}
}