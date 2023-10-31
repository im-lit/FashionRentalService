package com.example.fashionrentalservice.service;


import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.fashionrentalservice.exception.StaffNotFoundByID;
import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.dto.account.AccountDTO;
import com.example.fashionrentalservice.model.dto.account.WalletDTO;
import com.example.fashionrentalservice.model.request.WalletRequestEntity;
import com.example.fashionrentalservice.model.response.WalletResponseEntity;
import com.example.fashionrentalservice.repositories.AccountRepository;
import com.example.fashionrentalservice.repositories.WalletRepository;
@Service
public class WalletService {
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
    public WalletResponseEntity updateBalance(int walletID, double balance) throws StaffNotFoundByID {
        WalletDTO dto = walletRepo.findById(walletID).orElse(null);
        if(dto == null)
        	throw new StaffNotFoundByID();
        dto.setBalance(balance);
        return WalletResponseEntity.fromWalletDTO(walletRepo.save(dto));
    }
    
	
    //================================== Xóa Staff bởi ID========================================  
    public WalletResponseEntity deleteWallet(int walletID) throws CrudException {
    	WalletDTO dto = walletRepo.findById(walletID).orElse(null);
    	if(dto == null)
        	throw new StaffNotFoundByID();
    	walletRepo.deleteById(walletID);
        return WalletResponseEntity.fromWalletDTO(dto);
    }
}
