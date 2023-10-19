package com.example.fashionrentalservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRentalPricesRequestEntity {

	private double rentPrice1;
	
	private double rentPrice4;
	
	private double rentPrice7;
	
	private double rentPrice10;
	
	private double rentPrice14;
	
	private int productID;
}
