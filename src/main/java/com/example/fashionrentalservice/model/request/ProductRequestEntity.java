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

	private double price;

	private String status;

	private boolean forSale;

	private boolean forRent;

	private int categoryID;
	
	private int productownerID;
	
	private String productSpecificationData;
	
}
