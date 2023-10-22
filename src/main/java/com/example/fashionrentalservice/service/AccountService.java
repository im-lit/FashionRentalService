package com.example.fashionrentalservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fashionrentalservice.exception.AccNotFoundByID;
import com.example.fashionrentalservice.exception.EmailExisted;
import com.example.fashionrentalservice.exception.LoginFail;
import com.example.fashionrentalservice.exception.UpdatePasswordFail;
import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.dto.account.AccountDTO;
import com.example.fashionrentalservice.model.dto.account.AccountDTO.AccountStatus;
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
	public AccountResponseEntity login(String email, String password) throws CrudException {
		AccountDTO accountDTO= accRepo.checkLoginAccountByEmailAndPassword(email, password);
		if(accountDTO!=null) {
			
			return AccountResponseEntity.fromAccountDto(accountDTO);
		}
		
		throw new LoginFail();
	}
//================================== Tạo mới Account ========================================
    public AccountResponseEntity createNewAccount(AccountRequestEntity entity) throws CrudException{
        AccountDTO dto = AccountDTO.builder()
                .email(entity.getEmail())
                .password(entity.getPassword())
                .status(AccountStatus.NOT_VERIFIED)
                .reportedCount(0)
                .roleDTO(roleRepo.findById(entity.getRoleID()).orElseThrow())
                .build();
       AccountDTO emailChecked = accRepo.findByEmail((entity.getEmail()));
       if(emailChecked!=null)
    	   throw new EmailExisted();
       
        return AccountResponseEntity.fromAccountDto(accRepo.save(dto));
    }
  //================================== Update Password Account ========================================   
    public AccountResponseEntity updatePasswordAccount(int accountID,String password) throws CrudException {
        AccountDTO dto = (AccountDTO)accRepo.findById(accountID).orElseThrow();
        if(dto.getPassword().equals(password)) {
        	throw new UpdatePasswordFail();
        }
        dto.setPassword(password);    
        	return AccountResponseEntity.fromAccountDto(accRepo.save(dto));
    }
    
    //================================== Update Password Account ========================================   
    public AccountResponseEntity updateStatusAccount(int accountID,AccountStatus status) throws CrudException {
        AccountDTO dto = (AccountDTO)accRepo.findById(accountID).orElseThrow();
        dto.setStatus(status);    
        	return AccountResponseEntity.fromAccountDto(accRepo.save(dto));
    }
    

//================================== Lay tat ca account========================================
	public List<AccountResponseEntity> getAllAccount() {
		return accRepo.findAll().stream().map(AccountResponseEntity::fromAccountDto).collect(Collectors.toList());

	}
//================================== Lay account bởi ID========================================	
	public AccountResponseEntity getAccountByID(int accountID) throws CrudException{
		AccountDTO dto = accRepo.findById(accountID).orElse(null);
		if(dto==null) 
			throw new AccNotFoundByID();
		return AccountResponseEntity.fromAccountDto(dto);
		}
//================================== Xóa Account========================================
    public AccountResponseEntity deleteExistedAccount(int id) {
        AccountDTO dto = accRepo.findById(id).orElseThrow();
        accRepo.deleteById(id);

        return AccountResponseEntity.fromAccountDto(dto);
    }
	
}
	
	
