package com.example.fashionrentalservice.model.request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoucherRequestEntity {

    
	private String voucherCode;
 
	private String voucherName;
 	
 	//private LocalDate createdtDate;
 	private LocalDate startDate;
	
 	private LocalDate endDate;
	
 	
	private String description;
 	
 	
	private int quantity;
	private int maxDiscount;
 	
	private int discountAmount;
	private int voucherTypeID;
	private int productownerID;
}
