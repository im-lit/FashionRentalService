package com.example.fashionrentalservice.model.response;

import java.time.LocalDate;

import com.example.fashionrentalservice.model.dto.account.TransactionHistoryDTO;
import com.example.fashionrentalservice.model.dto.order.OrderBuyDTO;
import com.example.fashionrentalservice.model.dto.order.OrderRentDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionHistoryResponseEntity {
	private int transactionHistoryID;
	
	private String transactionType;
	
	private double amount;
	
	private String description;
	
	private LocalDate transactionDate;
	
	private OrderBuyDTO orderBuyDTO;
	
	private OrderRentDTO orderRentDTO;
	
	private int accountID;
	
	public static TransactionHistoryResponseEntity fromTransactionHistoryDTO(TransactionHistoryDTO dto) {
		return TransactionHistoryResponseEntity.builder()
				.transactionHistoryID(dto.getTransactionHistoryID())
				.transactionType(dto.getTransactionType())
				.amount(dto.getAmount())
				.description(dto.getDescription())
				.transactionDate(dto.getTransactionDate())
				.orderBuyDTO(dto.getOrderBuyDTO())
				.orderRentDTO(dto.getOrderRentDTO())
				.accountID(dto.getAccountDTO().getAccountID())
				.build();
	}
}
