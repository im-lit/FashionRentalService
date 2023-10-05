package com.example.fashionrentalservice.model.response;

import com.example.fashionrentalservice.model.dto.account.AccountDTO;
import com.example.fashionrentalservice.model.dto.account.RoleDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponseEntity {

	private int accountID;

	private String password;

	private String email;

	private RoleDTO roleDTO;
	

	public static AccountResponseEntity fromAccountDto(AccountDTO dto) {
        return AccountResponseEntity.builder()
                .accountID(dto.getAccountID())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .roleDTO(dto.getRoleDTO())
                .build();   
	}
}
