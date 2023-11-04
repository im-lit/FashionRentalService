package com.example.fashionrentalservice.service;


import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fashionrentalservice.exception.BalanceNegative;
import com.example.fashionrentalservice.exception.StaffNotFoundByID;
import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.dto.account.AccountDTO;
import com.example.fashionrentalservice.model.dto.account.WalletDTO;
import com.example.fashionrentalservice.model.request.WalletRequestEntity;
import com.example.fashionrentalservice.model.response.WalletResponseEntity;
import com.example.fashionrentalservice.repositories.AccountRepository;
import com.example.fashionrentalservice.repositories.WalletRepository;
@Service
public class PaymentBuyService {
	@Autowired
	private WalletRepository walletRepo;
	
	@Autowired
	private AccountRepository accRepo;
	
	
	public WalletResponseEntity getWalletByAccountID(int accountID) throws CrudException{

		WalletDTO dto = walletRepo.findByAccountID(accountID);
		if(dto==null) 
			throw new StaffNotFoundByID();
		
		return WalletResponseEntity.fromWalletDTO(dto);
	}
	
	//================================== Tao Wallet ========================================
    public WalletResponseEntity createWallet(WalletRequestEntity entity) throws CrudException{
    	
    	AccountDTO check = accRepo.findById(entity.getAccountID()).orElse(null);
    	if(check == null)
    	throw new StaffNotFoundByID();
    		
        	WalletDTO dto = WalletDTO.builder()
                    .balance(entity.getBalance())
                    .createdDate(LocalDate.now())
                    .accountDTO(accRepo.findById(entity.getAccountID()).orElseThrow())
                    .build();
            return WalletResponseEntity.fromWalletDTO(walletRepo.save(dto));
    }
    
  //================================== Update Balance ========================================
    public WalletResponseEntity updateBalance(int walletID, double balance) throws StaffNotFoundByID, CrudException {
        WalletDTO dto = walletRepo.findById(walletID).orElse(null);
        if(dto == null)
        	throw new StaffNotFoundByID();
        double oldBalance = dto.getBalance();
        double newBalance = oldBalance + balance;
        if (newBalance < 0) {
            throw new BalanceNegative();
        }
        dto.setBalance(newBalance);
        return WalletResponseEntity.fromWalletDTO(walletRepo.save(dto));
    }
    //================================== Update Balance ========================================
    public WalletResponseEntity updateBalanceAfterStuff(int walletID, double balance) throws StaffNotFoundByID, CrudException {
        WalletDTO dto = walletRepo.findById(walletID).orElse(null);
        if(dto == null)
        	throw new StaffNotFoundByID();
        double oldBalance = dto.getBalance();
        double newBalance = oldBalance - balance;
        if (newBalance < 0) {
            throw new BalanceNegative();
        }
        dto.setBalance(newBalance);
        return WalletResponseEntity.fromWalletDTO(walletRepo.save(dto));
    }
	
    //================================== Xóa Staff bởi ID========================================  
    public WalletResponseEntity deleteWallet(int walletID) throws CrudException {
    	WalletDTO dto = walletRepo.findById(walletID).orElse(null);   	
    	if(dto == null) {
        	throw new StaffNotFoundByID();
    	}
    	AccountDTO acc = dto.getAccountDTO();
    	if (dto != null) {
    		dto.setAccountDTO(null); 
    	    walletRepo.save(dto);
    	}
    	walletRepo.deleteById(walletID);
    	dto.setAccountDTO(acc);
        return WalletResponseEntity.fromWalletDTO(dto);
    }
}
