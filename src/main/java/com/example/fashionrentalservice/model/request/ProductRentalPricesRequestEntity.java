package com.example.fashionrentalservice.model.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRentalPricesRequestEntity {
	
	private List<Double> rentPrice;
	
	
	private List<String> mockDay;
	
	@JsonProperty("productID")
	private int productID;
}
