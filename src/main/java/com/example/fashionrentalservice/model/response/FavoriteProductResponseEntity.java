package com.example.fashionrentalservice.model.response;


import java.util.List;
import java.util.stream.Collectors;

import com.example.fashionrentalservice.model.dto.account.CustomerDTO;
import com.example.fashionrentalservice.model.dto.product.FavoriteProductDTO;
import com.example.fashionrentalservice.model.dto.product.ProductDTO;

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
	

	private ProductDTO productDTO;
	private CustomerDTO customerDTO;
	
	private String status;
	
	public static FavoriteProductResponseEntity fromFavoriteProductDTO(FavoriteProductDTO dto) {
		return FavoriteProductResponseEntity.builder()
				.favoriteProductID(dto.getFavoriteProductID())	
				.productDTO(dto.getProductDTO())
				.customerDTO(dto.getCustomerDTO())
				.status(dto.getStatus().toString())
				.build();
	}
	
	public static List<FavoriteProductResponseEntity> fromListFavoriteProductDTO(List<FavoriteProductDTO> dtos){
		return dtos.stream()
	            .map(dto -> FavoriteProductResponseEntity.builder()
	            		.favoriteProductID(dto.getFavoriteProductID())
	            		.productDTO(dto.getProductDTO())
	    				.customerDTO(dto.getCustomerDTO())
						.status(dto.getStatus().toString())
						.build())
	            .collect(Collectors.toList());
	}
	
	
	
	
}
