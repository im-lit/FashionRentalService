package com.example.fashionrentalservice.model.response;

import java.time.LocalDate;

import com.example.fashionrentalservice.model.dto.order.OrderRentDetailDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRentDetailStartDateEndDateResponseEntity {
	private int orderRentDetailID;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	
	public static OrderRentDetailStartDateEndDateResponseEntity fromOrderRentDetailDTO(OrderRentDetailDTO dto) {
		return OrderRentDetailStartDateEndDateResponseEntity.builder()
				 .orderRentDetailID(dto.getOrderRentDetailID())
                 .startDate(dto.getStartDate())
                 .endDate(dto.getEndDate())
                 .build();
	}
}
