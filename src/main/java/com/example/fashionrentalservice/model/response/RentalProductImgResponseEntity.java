package com.example.fashionrentalservice.model.response;

import java.util.List;
import java.util.stream.Collectors;

import com.example.fashionrentalservice.model.dto.order.RentalProductImgDTO;

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
	
	private String status;
	
	private int accountID;
	
	private int orderRentID;
	
	
	
	
	
	public static List<RentalProductImgResponseEntity> fromListRentalProductImgDTO(List<RentalProductImgDTO> dtos) {
		return dtos.stream()
	            .map(dto -> RentalProductImgResponseEntity.builder()
	                    .rentalProductImgID(dto.getRentalProductImgID())
	                    .imgUrl(dto.getProductImg())
	                    .status(dto.getStatus().toString())
	                    .createdDate(dto.getCreatedDate().toString())
	                    .accountID(dto.getAccountDTO().getAccountID())
	                    .orderRentID(dto.getOrderRentDTO().getOrderRentID())
	                    .build())
	            .collect(Collectors.toList());
	}
	
	public static RentalProductImgResponseEntity fromRentalProductImgDTO(RentalProductImgDTO dto) {
		return RentalProductImgResponseEntity.builder()
                .rentalProductImgID(dto.getRentalProductImgID())
                .imgUrl(dto.getProductImg())
                .status(dto.getStatus().toString())
                .createdDate(dto.getCreatedDate().toString())
                .accountID(dto.getAccountDTO().getAccountID())
                .orderRentID(dto.getOrderRentDTO().getOrderRentID())
				.build();
	}
}
