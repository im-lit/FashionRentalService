package com.example.fashionrentalservice.model.response;

import java.time.LocalDate;

import com.example.fashionrentalservice.model.dto.product.RequestAddingProductDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddingProductResponseEntity {
	private int requestAddingProductID;
	
	private LocalDate createdDate;
	
	private String description;
	
	private String status;
	
	private int productID;
	
	public static AddingProductResponseEntity fromRequestAddingProductDTO(RequestAddingProductDTO dto) {
		return AddingProductResponseEntity.builder()
				.requestAddingProductID(dto.getRequestAddingProductID())
				.createdDate(dto.getCreatedtDate())
				.description(dto.getDescription())
				.status(dto.getStatus().toString())
				.productID(dto.getProductDTO().getProductID())
				.build();
	}
}
