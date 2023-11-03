package com.example.fashionrentalservice.model.response;

import java.util.List;
import java.util.stream.Collectors;

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

	private double rentPrice;
	
	private int mockDay;
	
	
	private int productID;
	
	

	public static ProductRentalPricesResponseEntity fromProductRentalPricesDTO(ProductRentalPricesDTO dto) {
		return ProductRentalPricesResponseEntity.builder()
				.productrentalpricesID(dto.getProductRentalPricesID())				
				.rentPrice(dto.getRentPrice())
				.mockDay(dto.getMockDay())
				.productID(dto.getProductDTO().getProductID())
				.build();
	}
	
	public static List<ProductRentalPricesResponseEntity> fromListProductRentalPricesDTO(List<ProductRentalPricesDTO> dtos) {
		return dtos.stream()
	            .map(dto -> ProductRentalPricesResponseEntity.builder()
	                    .productrentalpricesID(dto.getProductRentalPricesID())
	                    .rentPrice(dto.getRentPrice())
	                    .mockDay(dto.getMockDay())
	                    .productID(dto.getProductDTO().getProductID())
	                    .build())
	            .collect(Collectors.toList());
	}
}
