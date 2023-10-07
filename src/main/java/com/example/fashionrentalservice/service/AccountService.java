package com.example.fashionrentalservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fashionrentalservice.model.dto.account.AccountDTO;
import com.example.fashionrentalservice.model.response.AccountResponseEntity;
import com.example.fashionrentalservice.repositories.AccountRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accRepo;


//================================== CheckLogin========================================
	public AccountResponseEntity login(String email, String password) {
		AccountDTO accountDTO = accRepo.checkLoginAccountByEmailAndPassword(email, password);
		return AccountResponseEntity.fromAccountDto(accountDTO);
	}

//================================== Lay tat ca account========================================
	public List<AccountResponseEntity> getAllAccount() {
		return accRepo.findAll().stream().map(AccountResponseEntity::fromAccountDto).collect(Collectors.toList());

	}

//	-------------Role kieu dep-------------
//	public List<AccountDTO> getAllAccount() {
//	    List<AccountDTO> accounts = accRepo.findAll();
//	    
//	    List<AccountDTO> accountDTOs = accounts.stream()
//	        .map(account -> {
//	            AccountDTO dto = new AccountDTO();
//	            dto.setAccountID(account.getAccountID());
//	            dto.setEmail(account.getEmail());
//	            dto.setPassword(account.getPassword());
//	            dto.setRoleDTO(account.getRoleDTO());
//	            return dto;
//	        })
//	        .collect(Collectors.toList());
//
//		return accountDTOs;
//		
//	}

//	public AccountCusResponseEntity loginCus(String email, String password) {
//	AccountDTO accountDTO= accRepo.checkLoginAccountByEmailAndPassword(email, password);
//	return AccountCusResponseEntity.fromAccountDto(accountDTO);
//}
//
//public AccountPOResponseEntity loginPO(String email, String password) {
//	Account
//}DTO accountDTO= accRepo.checkLoginAccountByEmailAndPassword(email, password);
//	return AccountPOResponseEntity.fromAccountDto(accountDTO);
}