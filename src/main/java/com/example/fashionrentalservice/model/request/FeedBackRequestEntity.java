package com.example.fashionrentalservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedBackRequestEntity {
	private String description;
	
	private int ratingPoint;
	
	private int productID;
	
	private int customerID;
	
	private int orderRentID;
	

	
}
