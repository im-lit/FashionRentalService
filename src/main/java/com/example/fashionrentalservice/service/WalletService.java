package com.example.fashionrentalservice.service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fashionrentalservice.exception.BalanceNegative;
import com.example.fashionrentalservice.exception.PendingMoneyNegative;
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
    
  //================================== Cong(+) tien Balance ========================================
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
    //================================== Tru`(-) tien Balance ========================================
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
    
  //================================== Tru`(-) tien Balance returnDTO cho createOrder ========================================
    public WalletDTO updateBalanceReturnDTO(int walletID, double balance) throws  CrudException {
        WalletDTO dto = walletRepo.findById(walletID).orElse(null);
        if(dto == null)
        	throw new StaffNotFoundByID();
        double oldBalance = dto.getBalance();
        double newBalance = oldBalance - balance;
        if (newBalance < 0) {
            throw new BalanceNegative();
        }
        dto.setBalance(newBalance);
        return dto;
    }
    
    
  //================================== Cong(+) tien PendingMoney returnDTO cho createOrder ========================================
    public WalletDTO updatePendingMoneyReturnDTO(int walletID, double pendingMoney) throws  CrudException {
        WalletDTO dto = walletRepo.findById(walletID).orElse(null);
        if(dto == null)
        	throw new StaffNotFoundByID();
        double oldPendingMoney = dto.getPendingMoney();
        double newPendingMoney = oldPendingMoney + pendingMoney;
        if (newPendingMoney < 0) {
            throw new BalanceNegative();
        }
        dto.setPendingMoney(newPendingMoney);
        return dto;
    }
    
    //================================== Tru(-) tien PendingMoney  (+) vao balance returnDTO cho updateStatusOrder ========================================
	public WalletDTO updatePendingMoneyToBalanceReturnDTO(int walletID, double orderTotal) throws CrudException {
		WalletDTO dto = walletRepo.findById(walletID).orElse(null);
		double oldPendingMoney;
		double newPendingMoney;
		double newBalance;
		
		if (dto == null)
			throw new StaffNotFoundByID();

		oldPendingMoney = dto.getPendingMoney();
		newPendingMoney = oldPendingMoney - orderTotal;
		if (newPendingMoney < 0) {
			throw new PendingMoneyNegative();
		}

		dto.setPendingMoney(newPendingMoney);

		newBalance = dto.getBalance() + orderTotal;
		if (newBalance < 0) {
			throw new BalanceNegative();
		}

		dto.setBalance(newBalance);

		return walletRepo.save(dto);
	}
	
    //================================== Tru(-) tien PendingMoney  (+) vao balance Customer returnDTO cho updateStatusOrder ========================================
	public WalletDTO updatePendingMoneyToCustomerBalanceReturnDTO(WalletDTO cusWallet, WalletDTO poWallet,double orderTotal) throws CrudException {
		double newPendingMoney;
		double newBalance;
		List<WalletDTO> list = new ArrayList<>();
		
		newPendingMoney = poWallet.getPendingMoney() - orderTotal;
		if (newPendingMoney < 0) {
			throw new PendingMoneyNegative();
		}
		poWallet.setPendingMoney(newPendingMoney);
		list.add(poWallet);
		
		newBalance = cusWallet.getBalance() + orderTotal;
		if (newBalance < 0) {
			throw new BalanceNegative();
		}
		cusWallet.setBalance(newBalance);
		list.add(cusWallet);
		
		walletRepo.saveAll(list);
		return cusWallet;
	}
	
    //================================== Xóa Wallet bởi ID========================================  
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
