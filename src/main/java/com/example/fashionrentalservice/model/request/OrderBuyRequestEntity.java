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
public class OrderBuyRequestEntity {

	
	private double totalBuyPriceProduct;
	
	private double shippingFee;
	
	private double total;
	
	private String customerAddress;
	
	private int customerID;
	
	private int productownerID;
	
	private List<OrderBuyDetailRequestEntity> orderDetail;
	
}
