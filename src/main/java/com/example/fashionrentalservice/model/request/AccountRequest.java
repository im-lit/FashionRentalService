package com.example.fashionrentalservice.model.request;

import com.example.fashionrentalservice.model.dto.account.RoleDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {

	private String password;

	private String email;

	private RoleDTO roleDTO;

}