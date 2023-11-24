package com.example.fashionrentalservice.model.response;

import java.time.LocalDate;

import com.example.fashionrentalservice.model.dto.order.OrderBuyDTO;
import com.example.fashionrentalservice.model.dto.order.OrderRentDTO;
import com.example.fashionrentalservice.model.dto.product.RequestComplainingOrderDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestComplainingOrderResponseEntity {
	private int requestComplainingOrderID;
	
	private LocalDate createdDate;
	
	private double expectedCost;
	
	private String description;
	
	private String staffResponse;
	
	private String status;
	
	private OrderRentDTO orderRentDTO;
	
	private OrderBuyDTO orderBuyDTO;
	
	private int productownerID;
	
	public static RequestComplainingOrderResponseEntity fromRequestComplainingOrderDTO(RequestComplainingOrderDTO dto) {
		return RequestComplainingOrderResponseEntity.builder()
				.requestComplainingOrderID(dto.getRequestComplainingOrderID())
				.createdDate(dto.getCreatedtDate())
				.expectedCost(dto.getExpectedCost())
				.description(dto.getDescription())
				.staffResponse(dto.getStaffResponse())
				.status(dto.getStatus().toString())
				.orderRentDTO(dto.getOrderRentDTO())
				.orderBuyDTO(dto.getOrderBuyDTO())
				.productownerID(dto.getProductownerDTO().getProductownerID())
				.build();
	}
}
