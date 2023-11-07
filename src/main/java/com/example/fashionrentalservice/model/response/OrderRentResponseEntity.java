package com.example.fashionrentalservice.model.response;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.example.fashionrentalservice.model.dto.order.OrderRentDTO;
import com.example.fashionrentalservice.model.dto.order.OrderRentDTO.OrderRentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRentResponseEntity {
	private int orderRentID;
	
	private double total;
	
	private OrderRentStatus status;
	
	private LocalDate dateOrder;
	
	private int customerID;
	
	private int productownerID;
	
	public static List<OrderRentResponseEntity> fromListOrderRentDTO(List<OrderRentDTO> dtos) {
		return dtos.stream()
	            .map(dto -> OrderRentResponseEntity.builder()
	                    .orderRentID(dto.getOrderRentID())
	                    .total(dto.getTotal())
	                    .dateOrder(dto.getDateOrder())
	                    .status(dto.getStatus())
	                    .customerID(dto.getCustomerDTO().getCustomerID())
	                    .productownerID(dto.getProductownerDTO().getProductownerID())
	                    .build())
	            .collect(Collectors.toList());
	}
	
	public static OrderRentResponseEntity fromOrderRentDTO(OrderRentDTO dto) {
		return OrderRentResponseEntity.builder()
                .orderRentID(dto.getOrderRentID())
                .total(dto.getTotal())
                .status(dto.getStatus())
                .dateOrder(dto.getDateOrder())
                .customerID(dto.getCustomerDTO().getCustomerID())
                .productownerID(dto.getProductownerDTO().getProductownerID())
				.build();
	}
}
