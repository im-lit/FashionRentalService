package com.example.fashionrentalservice.model.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRentRequestEntity {

	private double total;
		
	private String customerAddress;
	
	private int customerID;
	
	private int productownerID;
	
	private List<OrderRentDetailRequestEntity> orderRentDetail;
	
}
