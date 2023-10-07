package com.example.fashionrentalservice.model.response;

import com.example.fashionrentalservice.model.dto.account.AccountDTO;
import com.example.fashionrentalservice.model.dto.account.CustomerDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseEntity {

		private int customerID;
		

		private String fullName;
		
		private String phone;
		
		private boolean sex;
		
		private boolean status;
		
		private String avatarUrl;
		
		private double balance;
		
	    private AccountDTO accountDTO;
	    
		public static CustomerResponseEntity fromCustomerDTO(CustomerDTO dto) {
	        return CustomerResponseEntity.builder()
	                .customerID(dto.getCustomerID())
	                .fullName(dto.getFullName())
	                .phone(dto.getPhone())
	                .avatarUrl(dto.getAvatarUrl())
	                .balance(dto.getBalance())
	                .accountDTO(dto.getAccountDTO())
	                .build();   
		}
}
