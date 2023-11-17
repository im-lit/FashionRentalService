package com.example.fashionrentalservice.model.response;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.lang.Nullable;

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
	
	private double totalRentPriceProduct;
	
	private double shippingFee;
	
	private double total;
	
	private String customerAddress;
	
	private double cocMoneyTotal;
	
	private OrderRentStatus status;
	
	private LocalDate dateOrder;
	
	private String orderCode;
	
	private String productownerName;
	
	private String customerName;
	
	private int customerID;
	
	private int productownerID;
	
	
	public static List<OrderRentResponseEntity> fromListOrderRentDTO(List<OrderRentDTO> dtos) {
		return dtos.stream()
	            .map(dto -> OrderRentResponseEntity.builder()
	                    .orderRentID(dto.getOrderRentID())
	                    .total(dto.getTotal())
	                    .totalRentPriceProduct(dto.getTotalRentPriceProduct())
	                    .shippingFee(dto.getShippingFee())
	                    .customerAddress(dto.getCustomerAddress())
	                    .cocMoneyTotal(dto.getCocMoneyTotal())
	                    .dateOrder(dto.getDateOrder())
	                    .status(dto.getStatus())
	                    .orderCode(dto.getOrderCode())
	                    .productownerName(dto.getProductownerDTO().getFullName())
	                    .customerName(dto.getCustomerDTO().getFullName())
	                    .customerID(dto.getCustomerDTO().getCustomerID())
	                    .productownerID(dto.getProductownerDTO().getProductownerID())
	                    .build())
	            .collect(Collectors.toList());
	}
	
	public static OrderRentResponseEntity fromOrderRentDTO(OrderRentDTO dto) {
		return OrderRentResponseEntity.builder()
                .orderRentID(dto.getOrderRentID())
                .total(dto.getTotal())
                .totalRentPriceProduct(dto.getTotalRentPriceProduct())
                .shippingFee(dto.getShippingFee())
                .customerAddress(dto.getCustomerAddress())
                .cocMoneyTotal(dto.getCocMoneyTotal())
                .status(dto.getStatus())
                .dateOrder(dto.getDateOrder())
                .orderCode(dto.getOrderCode())
                .customerID(dto.getCustomerDTO().getCustomerID())
                .productownerID(dto.getProductownerDTO().getProductownerID())
				.build();
	}
}
