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
	
	private String productAvt;
	
	private double price;

	private boolean forSale;

	private boolean forRent;
	
	private String productOwnerName;
	
	private ProductRentalPricesDTO productRentalPricesDTO;
	
	
	public static ProductSlimResponseEntity fromProductDTO(ProductDTO dto) {
		return ProductSlimResponseEntity.builder()
				.productID(dto.getProductID())
				.productName(dto.getProductName())
				.price(dto.getPrice())
				.forRent(dto.isForRent())
				.forSale(dto.isForSale())
				.productOwnerName(dto.getProductownerDTO().getFullName())
				.productRentalPricesDTO(dto.getProductRentalPricesDTO())
				.build();
	}
}
