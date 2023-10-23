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
		
		private String avatarUrl;
			
	    private AccountDTO accountDTO;
	    
		public static CustomerResponseEntity fromCustomerDTO(CustomerDTO dto) {
	        return CustomerResponseEntity.builder()
	                .customerID(dto.getCustomerID())
	                .fullName(dto.getFullName())
	                .phone(dto.getPhone())
	                .sex(dto.isSex())
	                .avatarUrl(dto.getAvatarUrl())
	                .accountDTO(dto.getAccountDTO())
	                .build();   
		}
}
