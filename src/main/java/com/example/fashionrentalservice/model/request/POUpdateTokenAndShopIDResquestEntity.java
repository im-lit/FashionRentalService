package com.example.fashionrentalservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class POUpdateTokenAndShopIDResquestEntity {
	
	    private String POToken;
		
		private int POShopID;
	

}
