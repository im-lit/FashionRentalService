package com.example.fashionrentalservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fashionrentalservice.model.dto.account.AccountDTO;
import com.example.fashionrentalservice.model.request.AccountRequestEntity;
import com.example.fashionrentalservice.model.response.AccountResponseEntity;
import com.example.fashionrentalservice.repositories.AccountRepository;
import com.example.fashionrentalservice.repositories.RoleRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accRepo;
	
	@Autowired
	private RoleRepository roleRepo;


//================================== CheckLogin========================================
	public AccountResponseEntity login(String email, String password) {
		AccountDTO accountDTO = accRepo.checkLoginAccountByEmailAndPassword(email, password);
		return AccountResponseEntity.fromAccountDto(accountDTO);
	}
//================================== Tạo mới Account ========================================
    public AccountResponseEntity createNewAccount(AccountRequestEntity entity) {
        AccountDTO dto = AccountDTO.builder()
                .email(entity.getEmail())
                .password(entity.getPassword())
                .roleDTO(roleRepo.findById(entity.getRoleID()).orElseThrow())
                .build();

        return AccountResponseEntity.fromAccountDto(accRepo.save(dto));
    }
  //================================== Update Account ========================================   
    public AccountResponseEntity updatePasswordAccount(int accountID,String password) {
        AccountDTO dto = (AccountDTO)accRepo.findById(accountID).orElseThrow();
        dto.setPassword(password);
        return AccountResponseEntity.fromAccountDto(accRepo.save(dto));
    }
    

//================================== Lay tat ca account========================================
	public List<AccountResponseEntity> getAllAccount() {
		return accRepo.findAll().stream().map(AccountResponseEntity::fromAccountDto).collect(Collectors.toList());

	}
}