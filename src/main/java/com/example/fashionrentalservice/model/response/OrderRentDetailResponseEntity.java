package com.example.fashionrentalservice.model.response;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.example.fashionrentalservice.model.dto.order.OrderRentDetailDTO;
import com.example.fashionrentalservice.model.dto.product.ProductDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRentDetailResponseEntity {
	private int orderRentDetailID;
	
	private double cocMoney;
	
	private double rentPrice;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private ProductDTO productDTO;
	
	private int orderRentID;
	
	public static List<OrderRentDetailResponseEntity> fromListOrderRentDetailDTO(List<OrderRentDetailDTO> dtos) {
		return dtos.stream()
	            .map(dto -> OrderRentDetailResponseEntity.builder()
	                    .orderRentDetailID(dto.getOrderRentDetailID())
	                    .rentPrice(dto.getRentPrice())
	                    .cocMoney(dto.getCocMoney())
	                    .startDate(dto.getStartDate())
	                    .endDate(dto.getEndDate())
	                    .productDTO(dto.getProductDTO())
	                    .orderRentID(dto.getOrderRentDTO().getOrderRentID())
	                    .build())
	            .collect(Collectors.toList());
	}
	
	public static OrderRentDetailResponseEntity fromOrderRentDetailDTO(OrderRentDetailDTO dto) {
		return OrderRentDetailResponseEntity.builder()
				 .orderRentDetailID(dto.getOrderRentDetailID())
                 .rentPrice(dto.getRentPrice())
                 .productDTO(dto.getProductDTO())
                 .orderRentID(dto.getOrderRentDTO().getOrderRentID())
                 .build();
	}
}
