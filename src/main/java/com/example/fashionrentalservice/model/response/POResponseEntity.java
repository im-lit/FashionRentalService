package com.example.fashionrentalservice.model.response;

import com.example.fashionrentalservice.model.dto.account.AccountDTO;
import com.example.fashionrentalservice.model.dto.account.ProductOwnerDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class POResponseEntity {
	
	private int productownerID;
	
	private String fullName;
	
	private String phone;
	
	private String avatarUrl;
	
	private String address;
	
	private AccountDTO accountDTO;
	
	public static POResponseEntity fromPODTO(ProductOwnerDTO dto) {
        return POResponseEntity.builder()
                .productownerID(dto.getProductownerID())
                .fullName(dto.getFullName())
                .phone(dto.getPhone())
                .avatarUrl(dto.getAvatarUrl())
                .address(dto.getAddress())
                .accountDTO(dto.getAccountDTO())
                .build();   
	}
}
