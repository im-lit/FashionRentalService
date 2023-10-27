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
	
	private LocalDate updateDate;
	
	private LocalDate createDate;
	
	private String requestStatus;
	
	private String description;
	
	private int productID;
	
	private StaffDTO staffDTO;
	

	
	public static List<StaffRequestedResponseEntity> fromStaffRequestedDTO(List<StaffRequestedDTO> dtos) {
		return dtos.stream()
	            .map(dto -> StaffRequestedResponseEntity.builder()
	                    .staffRequestedID(dto.getStaffRequestedID())
	                    .createDate(dto.getRequestAddingProductDTO().getCreatedtDate())
	                    .updateDate(dto.getCreatedDate())
	                    .requestStatus(dto.getRequestAddingProductDTO().getStatus().toString())
	                    .description(dto.getRequestAddingProductDTO().getDescription())
	                    .productID(dto.getRequestAddingProductDTO().getProductDTO().getProductID())
	                    .staffDTO(dto.getStaffDTO())
	                    .build())
	            .collect(Collectors.toList());
	}
}
