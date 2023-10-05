package com.example.fashionrentalservice.model.response;

import com.example.fashionrentalservice.model.dto.account.AccountDTO;
import com.example.fashionrentalservice.model.dto.account.ProductOwnerDTO;
import com.example.fashionrentalservice.model.dto.account.RoleDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountPOResponseEntity {
	@JsonProperty("accountID")
	private int accountID;
	
	@JsonProperty("password")
	private String password;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("roleDTO")
	private RoleDTO roleDTO;
	

	@JsonProperty("productownerID")
	private int productownerID;

	public static AccountPOResponseEntity fromAccountDto(AccountDTO dto) {
        return AccountPOResponseEntity.builder()
                .accountID(dto.getAccountID())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .roleDTO(dto.getRoleDTO())
                .productownerID(dto.getProductOwnerDTO().getProductownerID())
                .build();   
	}
}
