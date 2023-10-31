package com.example.fashionrentalservice.model.response;

import java.time.LocalDate;

import com.example.fashionrentalservice.model.dto.account.WalletDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletResponseEntity {
	
    
	private int walletID;
	
	private double balance;
	
	private LocalDate createdDate;
	
    private int accountID;
    
	public static WalletResponseEntity fromWalletDTO(WalletDTO dto) {
		return WalletResponseEntity.builder()
				.walletID(dto.getWalletID())
				.balance(dto.getBalance())
				.createdDate(dto.getCreatedDate())
				.accountID(dto.getAccountDTO().getAccountID())
				.build();
	}
}
