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
import com.example.fashionrentalservice.model.dto.order.OrderRentDTO;
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
        double newBalance = (oldBalance - balance);
        if (newBalance < 0) {
            throw new BalanceNegative();
        }
        dto.setBalance(newBalance);
        return WalletResponseEntity.fromWalletDTO(walletRepo.save(dto));
    }
    
  //================================== Tru`(-) tien Balance returnDTO cho createOrder ========================================
    public WalletDTO updateBalanceReturnDTO(int walletID, double total) throws  CrudException {
        WalletDTO dto = walletRepo.findById(walletID).orElse(null);
        double oldBalance;
        double newBalance;
        if(dto == null)
        	throw new StaffNotFoundByID();
        oldBalance = dto.getBalance();
        newBalance = (oldBalance - total);
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
            throw new PendingMoneyNegative("PendingMoney can not be a negative number!");
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
            throw new PendingMoneyNegative("PendingMoney can not be a negative number!");
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
	
	
	//================================== Thuê ========================================
	
	
	
	  //================================== Tru`(-) tien CustomerBalance returnDTO cho createOrderRent ========================================
    public WalletDTO updateCusBalanceReturnDTO(int walletID, double balance) throws  CrudException {
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
    
    
	  //================================== cộng tiền OrderRent vào PO pendingMoney và CocMoney vào PO cocMoney ========================================
    public WalletDTO updatePOPendingMoneyAndCocMoneyReturnDTO(int walletID, double pendingMoney, double cocMoney) throws  CrudException {
        WalletDTO dto = walletRepo.findById(walletID).orElse(null);
        if(dto == null)
        	throw new StaffNotFoundByID();
        double oldPendingMoney = dto.getPendingMoney();
        double newPendingMoney = oldPendingMoney + pendingMoney;
        if (newPendingMoney < 0) {
            throw new PendingMoneyNegative("PendingMoney can not be a negative");
        }
        dto.setPendingMoney(newPendingMoney);
        
        double oldCocMoney = dto.getCocMoney();
        double newCocMoney = oldCocMoney + cocMoney;
        dto.setCocMoney(newCocMoney);
        return dto;
    }
    
    //================================== trừ tiền OrderRent pendingMoney và cộng tiền OrderRent vào PO Balance,  trả CocMoney từ PO vào  Cus Balance ========================================
    public WalletDTO updatePOPendingMoneyToBalanceAndRefundCocMoneyReturnDTO(WalletDTO cusWallet, WalletDTO poWallet, double rentProductTotal, double cocMoneyTotal) throws CrudException {
    	List<WalletDTO> list = new ArrayList<>();
    	double cocMoney;
    	double newPendingMoney;
    	double newBalancePO;
    	double newBalanceCus;
    	
    	newPendingMoney = poWallet.getPendingMoney() - rentProductTotal;
        if (newPendingMoney < 0) 
          throw new PendingMoneyNegative("PendingMoney can not be a negative number!");
    	poWallet.setPendingMoney(newPendingMoney);
    	
    	newBalancePO = poWallet.getBalance() + rentProductTotal;
    	poWallet.setBalance(newBalancePO);
    	
    	cocMoney = poWallet.getCocMoney() - cocMoneyTotal;
    	if (cocMoney < 0) 
            throw new PendingMoneyNegative("Coc Money can not be a neagtive number!");
    	poWallet.setCocMoney(cocMoney);
    	list.add(poWallet);
    	
    	newBalanceCus = cusWallet.getBalance() + cocMoneyTotal;
    	cusWallet.setBalance(newBalanceCus);  	
    	list.add(cusWallet);
    	
    	walletRepo.saveAll(list);

    	return cusWallet;
    }
    
    //================================== trừ tiền OrderRent trong pendingMoney của PO sau đó cộng tiền OrderRent vào Cus Balance,  trả CocMoney từ PO vào Cus Balance ========================================
    public WalletDTO updatePOPendingMoneyToCusBalanceAndRefundCocMoneyReturnDTO(WalletDTO cusWallet, WalletDTO poWallet, double rentProductTotal, double cocMoneyTotal) throws CrudException {
    List<WalletDTO> list = new ArrayList<>();
	double newCocMoney;
	double newPendingMoney;
	double newBalanceCus;
	
	newPendingMoney = poWallet.getPendingMoney() - rentProductTotal;
	if (newPendingMoney < 0) 
        throw new PendingMoneyNegative("Pending Money can not be a neagtive number!");
	poWallet.setPendingMoney(newPendingMoney);
	
	newCocMoney = poWallet.getCocMoney() - cocMoneyTotal;
	if (newCocMoney < 0) 
        throw new PendingMoneyNegative("Coc Money can not be a neagtive number!");
	poWallet.setCocMoney(newCocMoney);
	list.add(poWallet);
	
	newBalanceCus = cusWallet.getBalance() + rentProductTotal + cocMoneyTotal;
	cusWallet.setBalance(newBalanceCus);
	list.add(cusWallet);
	
	walletRepo.saveAll(list);
	
	return cusWallet;
    }
    
    //= lấy tiền Cọc trong PO trừ ExpectedCost và chuyển tiền cọc về CusBalance, Cộng tiền ExpectedCost vào PO Balance và tiền OrderRent từ pending Money chuyển sang PO Balance. ========================================
    public WalletDTO StaffUpdate(WalletDTO cusWallet, WalletDTO poWallet, double expectedCost, OrderRentDTO orderRent) throws CrudException {
    	List<WalletDTO> list = new ArrayList<>();
    	double poCocMoney;
    	double poNewCocMoney;
    	double newCocMoney;
    	double newCusBalance;
    	double poNewBalance;
    	double poNewPendingMoney;
    	
    	poCocMoney = poWallet.getCocMoney();
    	newCocMoney = orderRent.getCocMoneyTotal() - expectedCost;
    	if(newCocMoney < 0) 
    		throw new PendingMoneyNegative("Coc Money can not be a negative number");
    	//Hoàn Trả tiền cọc - tiền làm hư đồ lại vào Customer Balance .
    	newCusBalance = cusWallet.getBalance() + newCocMoney;
    	cusWallet.setBalance(newCusBalance);
    	list.add(cusWallet);
    	
    	//Trừ tiền cọc trong ví PO .
    	poNewCocMoney = poCocMoney - orderRent.getCocMoneyTotal();
    	if(poNewCocMoney < 0) 
    		throw new PendingMoneyNegative("ProductOwner Coc Money can not be a negative number");
    	poWallet.setCocMoney(poNewCocMoney);
    	
    	//Trừ tiền PendingMoney trong ví PO , tiền hóa đơn thuê hoàn thành rồi thì trừ pendingMOney để cộng tiền đó vào Balance.
    	poNewPendingMoney = poWallet.getPendingMoney() - orderRent.getTotalRentPriceProduct();
    	if(poNewPendingMoney < 0) 
    		throw new PendingMoneyNegative("ProductOwner Pending Money can not be a negative number");
    	poWallet.setPendingMoney(poNewPendingMoney);
    	
    	//Cộng tiền hóa đơn thuê và tiền bồi thường vào Po Balance.
    	poNewBalance = poWallet.getBalance() + orderRent.getTotalRentPriceProduct() + expectedCost;
    	if(poNewBalance < 0) 
    		throw new PendingMoneyNegative("ProductOwner Balance can not be a negative number");
    	poWallet.setBalance(poNewBalance);
    	list.add(poWallet);

    	walletRepo.saveAll(list);
    		
    	return poWallet;
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
