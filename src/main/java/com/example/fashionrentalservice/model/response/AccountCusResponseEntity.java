package com.example.fashionrentalservice.model.response;

import com.example.fashionrentalservice.model.dto.account.AccountDTO;
import com.example.fashionrentalservice.model.dto.account.CustomerDTO;
import com.example.fashionrentalservice.model.dto.account.ProductOwnerDTO;
import com.example.fashionrentalservice.model.dto.account.RoleDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountCusResponseEntity {

	private int accountID;

	private String password;

	private String email;

	private RoleDTO roleDTO;
	
	private int customerID;
	
//	private int productownerID;

	public static AccountCusResponseEntity fromAccountDto(AccountDTO dto) {
        return AccountCusResponseEntity.builder()
                .accountID(dto.getAccountID())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .roleDTO(dto.getRoleDTO())
                .customerID(dto.getCustomerDTO().getCustomerID())
//                .productownerID(dto.getProductOwnerDTO().getProductownerID())
                .build();   
	}
}
