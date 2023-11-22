package com.example.fashionrentalservice.model.response;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.example.fashionrentalservice.model.dto.order.OrderBuyDTO;
import com.example.fashionrentalservice.model.dto.order.OrderBuyDTO.OrderBuyStatus;
import com.example.fashionrentalservice.model.dto.product.VoucherDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderBuyResponseEntity {
	private int orderBuyID;
	
	private double total;
	
	private double shippingFee;
	
	private double totalBuyPriceProduct;
	
	private String customerAddress;
	
	private OrderBuyStatus status;
	
	private LocalDate dateOrder;
	
	private int customerID;
	
	private int productownerID;
	
	private String customerName;
	
	private String orderCode;
	
	private String productOwnerName;
	private int voucherID;
	
	public static List<OrderBuyResponseEntity> fromListOrderBuyDTO(List<OrderBuyDTO> dtos) {
		return dtos.stream()
	            .map(dto -> OrderBuyResponseEntity.builder()
	                    .orderBuyID(dto.getOrderBuyID())
	                    .total(dto.getTotal())
	                    .totalBuyPriceProduct(dto.getTotalBuyPriceProduct())
	                    .shippingFee(dto.getShippingfee())
	                    .customerAddress(dto.getCustomerAddress())
	                    .dateOrder(dto.getDateOrder())
	                    .status(dto.getStatus())
	                    .orderCode(dto.getOrderCode())
	                    .customerID(dto.getCustomerDTO().getCustomerID())
	                    .customerName(dto.getCustomerDTO().getFullName())
	                    .voucherID(dto.getVoucherDTO().getVoucherID())
	                    .productownerID(dto.getProductownerDTO().getProductownerID())
	                    .productOwnerName(dto.getProductownerDTO().getFullName())
	                    .build())
	            .collect(Collectors.toList());
	}
	
	public static OrderBuyResponseEntity fromOrderBuyDTO(OrderBuyDTO dto) {
		return OrderBuyResponseEntity.builder()
                .orderBuyID(dto.getOrderBuyID())
                .total(dto.getTotal())
                .totalBuyPriceProduct(dto.getTotalBuyPriceProduct())
                .shippingFee(dto.getShippingfee())
                .customerAddress(dto.getCustomerAddress())
                .status(dto.getStatus())
                .dateOrder(dto.getDateOrder())
                .orderCode(dto.getOrderCode())
                .customerID(dto.getCustomerDTO().getCustomerID())
                .customerName(dto.getCustomerDTO().getFullName())
                .voucherID(dto.getVoucherDTO().getVoucherID())
                .productownerID(dto.getProductownerDTO().getProductownerID())
                .productOwnerName(dto.getProductownerDTO().getFullName())
				.build();
	}
}
