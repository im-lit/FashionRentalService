package com.example.fashionrentalservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionHistoryRequestEntity {
	
	
	private String transactionType;
	
	private double amount;
	
	private String description;
	
	private int accountID;
}
