package com.example.fashionrentalservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestEntity {
	
	private String fullName;
	
	private String phone;
	
	private boolean sex;
	
	private boolean status;
	
	private String avatarUrl;
	
	private double balance;
}
