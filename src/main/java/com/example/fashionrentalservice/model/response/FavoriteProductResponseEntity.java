package com.example.fashionrentalservice.model.response;


import java.util.List;
import java.util.stream.Collectors;

import com.example.fashionrentalservice.model.dto.product.FavoriteProductDTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteProductResponseEntity {

	private int favoriteProductID;
	

	private int productID;
	private int customerID;
	
	private String status;
	
	public static FavoriteProductResponseEntity fromFavoriteProductDTO(FavoriteProductDTO dto) {
		return FavoriteProductResponseEntity.builder()
				.favoriteProductID(dto.getFavoriteProductID())	
				.customerID(dto.getCustomerDTO().getCustomerID())
				.productID(dto.getProductDTO().getProductID())
				.status(dto.getStatus().toString())
				.build();
	}
	
	public static List<FavoriteProductResponseEntity> fromListFavoriteProductDTO(List<FavoriteProductDTO> dtos){
		return dtos.stream()
	            .map(dto -> FavoriteProductResponseEntity.builder()
	            		.favoriteProductID(dto.getFavoriteProductID())
						.customerID(dto.getCustomerDTO().getCustomerID())
						.productID(dto.getProductDTO().getProductID())
						.status(dto.getStatus().toString())
						.build())
	            .collect(Collectors.toList());
	}
	
	
	
	
}
