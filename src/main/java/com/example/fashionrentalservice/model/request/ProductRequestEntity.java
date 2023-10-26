package com.example.fashionrentalservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestEntity {

	private String productName;
	
	private String productReceiptUrl;

	private String description;
	
	private String checkType;

	private double price;
	
	private String productAvt;

	private String status;

	private int categoryID;
	
	private int productownerID;
	
	private String productSpecificationData;
	
}
