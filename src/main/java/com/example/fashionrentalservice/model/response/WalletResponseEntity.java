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
	
	private double cocMoney;
	
	private LocalDate createdDate;
	
	private double pendingMoney;
	
    private int accountID;
    
	public static WalletResponseEntity fromWalletDTO(WalletDTO dto) {
		return WalletResponseEntity.builder()
				.walletID(dto.getWalletID())
				.balance(dto.getBalance())
				.cocMoney(dto.getCocMoney())
				.pendingMoney(dto.getPendingMoney())
				.createdDate(dto.getCreatedDate())
				.accountID(dto.getAccountDTO().getAccountID())
				.build();
	}
}
