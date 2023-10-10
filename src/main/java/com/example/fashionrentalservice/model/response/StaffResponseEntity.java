package com.example.fashionrentalservice.model.response;

import com.example.fashionrentalservice.model.dto.account.AccountDTO;
import com.example.fashionrentalservice.model.dto.account.StaffDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaffResponseEntity {

	private int staffID;

	private String fullName;
	
	private String avatarUrl;

	private boolean status;

	private AccountDTO accountDTO;

	public static StaffResponseEntity fromStaffDTO(StaffDTO dto) {
		return StaffResponseEntity.builder()
				.staffID(dto.getStaffID())
				.fullName(dto.getFullName())
				.avatarUrl(dto.getAvatarUrl())
				.status(dto.isStatus())
				.accountDTO(dto.getAccountDTO())
				.build();
	}
}
