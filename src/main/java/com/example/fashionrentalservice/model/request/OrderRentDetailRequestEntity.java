package com.example.fashionrentalservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRentDetailRequestEntity {
	
	private double cocMoney;
	
	private String startDate;
	
	private String endDate;
	
	private double rentPrice;
	
	private int productID;
	
	private int orderRentID;
	
}
