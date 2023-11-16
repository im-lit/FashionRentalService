package com.example.fashionrentalservice.model.response;

import java.util.List;
import java.util.stream.Collectors;

import com.example.fashionrentalservice.model.dto.order.RentalProductImgDTO;
import com.example.fashionrentalservice.model.dto.product.ProductImgDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalProductImgResponseEntity {
	private int rentalProductImgID;
	
	private String imgUrl;
	
	private String createdDate;
	
	private int accountID;
	
	private int orderRentDetailID;
	
	
	
	
	
	public static List<RentalProductImgResponseEntity> fromListRentalProductImgDTO(List<RentalProductImgDTO> dtos) {
		return dtos.stream()
	            .map(dto -> RentalProductImgResponseEntity.builder()
	                    .rentalProductImgID(dto.getRentalProductImgID())
	                    .imgUrl(dto.getProductImg())
	                    .createdDate(dto.getCreatedDate().toString())
	                    .accountID(dto.getAccountDTO().getAccountID())
	                    .orderRentDetailID(dto.getOrderRentDetailDTO().getOrderRentDetailID())
	                    .build())
	            .collect(Collectors.toList());
	}
	
	public static RentalProductImgResponseEntity fromRentalProductImgDTO(RentalProductImgDTO dto) {
		return RentalProductImgResponseEntity.builder()
                .rentalProductImgID(dto.getRentalProductImgID())
                .imgUrl(dto.getProductImg())
                .createdDate(dto.getCreatedDate().toString())
                .accountID(dto.getAccountDTO().getAccountID())
                .orderRentDetailID(dto.getOrderRentDetailDTO().getOrderRentDetailID())
				.build();
	}
}
