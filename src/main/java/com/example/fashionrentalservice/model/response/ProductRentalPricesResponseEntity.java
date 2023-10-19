package com.example.fashionrentalservice.model.response;

import com.example.fashionrentalservice.model.dto.product.ProductRentalPricesDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRentalPricesResponseEntity {
	
	private int productrentalpricesID;

	private double rentPrice1;
	
	private double rentPrice4;
	
	private double rentPrice7;
	
	private double rentPrice10;
	
	private double rentPrice14;
	
	private int productID;
	
	

	public static ProductRentalPricesResponseEntity fromProductRentalPricesDTO(ProductRentalPricesDTO dto) {
		return ProductRentalPricesResponseEntity.builder()
				.productrentalpricesID(dto.getProductRentalPricesID())
				.rentPrice1(dto.getRentPrice1())
				.rentPrice4(dto.getRentPrice4())
				.rentPrice7(dto.getRentPrice7())
				.rentPrice10(dto.getRentPrice10())
				.rentPrice14(dto.getRentPrice14())
				.productID(dto.getProductDTO().getProductID())
				.build();
	}
}
