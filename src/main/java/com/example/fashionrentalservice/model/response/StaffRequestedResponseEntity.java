package com.example.fashionrentalservice.model.response;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.example.fashionrentalservice.model.dto.account.StaffDTO;
import com.example.fashionrentalservice.model.dto.product.StaffRequestedDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaffRequestedResponseEntity {

	private int staffRequestedID;
	
	private LocalDate createdDate;
	
	private String requestStatus;
	
	private int productID;
	
	private StaffDTO staffDTO;
	

	
	public static List<StaffRequestedResponseEntity> fromStaffRequestedDTO(List<StaffRequestedDTO> dtos) {
		return dtos.stream()
	            .map(dto -> StaffRequestedResponseEntity.builder()
	                    .staffRequestedID(dto.getStaffRequestedID())
	                    .createdDate(dto.getCreatedDate())
	                    .requestStatus(dto.getRequestAddingProductDTO().getStatus().toString())
	                    .productID(dto.getRequestAddingProductDTO().getProductDTO().getProductID())
	                    .staffDTO(dto.getStaffDTO())
	                    .build())
	            .collect(Collectors.toList());
	}
}
