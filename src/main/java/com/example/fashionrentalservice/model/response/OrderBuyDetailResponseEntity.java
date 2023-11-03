package com.example.fashionrentalservice.model.response;

import java.util.List;
import java.util.stream.Collectors;

import com.example.fashionrentalservice.model.dto.order.OrderBuyDetailDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderBuyDetailResponseEntity {
	private int orderBuyDetailID;
	
	private double price;
	
	private int productID;
	
	private int orderBuyID;
	
	public static List<OrderBuyDetailResponseEntity> fromListOrderBuyDetailDTO(List<OrderBuyDetailDTO> dtos) {
		return dtos.stream()
	            .map(dto -> OrderBuyDetailResponseEntity.builder()
	                    .orderBuyDetailID(dto.getOrderBuyDetailID())
	                    .price(dto.getPrice())
	                    .productID(dto.getProductDTO().getProductID())
	                    .orderBuyID(dto.getOrderBuyDTO().getOrderBuyID())
	                    .build())
	            .collect(Collectors.toList());
	}
	
	public static OrderBuyDetailResponseEntity fromOrderBuyDetailDTO(OrderBuyDetailDTO dto) {
		return OrderBuyDetailResponseEntity.builder()
				 .orderBuyDetailID(dto.getOrderBuyDetailID())
                 .price(dto.getPrice())
                 .productID(dto.getProductDTO().getProductID())
                 .orderBuyID(dto.getOrderBuyDTO().getOrderBuyID())
                 .build();
	}
}
