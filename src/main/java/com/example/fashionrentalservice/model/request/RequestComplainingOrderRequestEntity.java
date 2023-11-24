package com.example.fashionrentalservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestComplainingOrderRequestEntity {
	private double expectedCost;
	
	private String description;
	
	private int orderRentID;
	
	private int productownerID;
}
