package com.example.fashionrentalservice.model.request;

import com.example.fashionrentalservice.model.dto.account.AccountDTO;
import com.example.fashionrentalservice.model.dto.order.OrderBuyDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyTransactionHistoryRequestEntity {
	
	private String transactionType;
	
	private double amount;
	
	private String description;
	
	private OrderBuyDTO orderBuyDTO;
	
	private AccountDTO accountDTO;
}
