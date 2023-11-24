package com.example.fashionrentalservice.service;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fashionrentalservice.exception.StaffNotFoundByID;
import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.dto.account.AccountDTO;
import com.example.fashionrentalservice.model.dto.account.TransactionHistoryDTO;
import com.example.fashionrentalservice.model.request.BuyTransactionHistoryRequestEntity;
import com.example.fashionrentalservice.model.request.TransactionHistoryRequestEntity;
import com.example.fashionrentalservice.model.response.TransactionHistoryResponseEntity;
import com.example.fashionrentalservice.repositories.AccountRepository;
import com.example.fashionrentalservice.repositories.TransactionHistoryRepository;
@Service
public class TransactionHistoryService {
	@Autowired
	private TransactionHistoryRepository transactionRepo;
	
	@Autowired
	private AccountRepository accRepo;
	
	public List<TransactionHistoryResponseEntity> getAllTransactionByAccountID(int accountID) throws CrudException{
		AccountDTO accDTO = accRepo.findById(accountID).orElse(null);
		if(accDTO==null) 
			throw new StaffNotFoundByID();		
		return  transactionRepo.findAllByAccountID(accountID).stream()
                .map(TransactionHistoryResponseEntity::fromTransactionHistoryDTO)
                .collect(Collectors.toList());
	}
	
	//================================== Tao Wallet ========================================
    public TransactionHistoryResponseEntity createTransactionHistory(TransactionHistoryRequestEntity entity) throws CrudException{   	    		
        	TransactionHistoryDTO dto = TransactionHistoryDTO.builder()
                    .transactionType(entity.getTransactionType())
                    .amount(entity.getAmount())
                    .transactionDate(LocalDate.now())
                    .description(entity.getDescription())
                    .accountDTO(accRepo.findById(entity.getAccountID()).orElseThrow())
                    .build();
            return TransactionHistoryResponseEntity.fromTransactionHistoryDTO(transactionRepo.save(dto));
    }
    
  //================================== Create Trans ========================================   
    public TransactionHistoryResponseEntity createBuyTransactionHistory(BuyTransactionHistoryRequestEntity entity) throws CrudException{   	    		
    	TransactionHistoryDTO dto = TransactionHistoryDTO.builder()
                .transactionType(entity.getTransactionType())
                .amount(entity.getAmount())
                .description(entity.getDescription())
                .transactionDate(LocalDate.now())
                .orderBuyDTO(entity.getOrderBuyDTO())
                .accountDTO(entity.getAccountDTO())
                .build();
        return TransactionHistoryResponseEntity.fromTransactionHistoryDTO(transactionRepo.save(dto));
}
    
  //================================== Create Trans ReturnDTO cho OrderService ========================================  
    public TransactionHistoryDTO createBuyTransactionHistoryReturnDTO(TransactionHistoryDTO entity) throws CrudException{   	    		
    	TransactionHistoryDTO dto = TransactionHistoryDTO.builder()
                .transactionType(entity.getTransactionType())
                .amount(entity.getAmount())
                .description(entity.getDescription())
                .transactionDate(LocalDate.now())
                .orderBuyDTO(entity.getOrderBuyDTO())
                .orderRentDTO(entity.getOrderRentDTO())
                .accountDTO(entity.getAccountDTO())
                .build();
        return dto;
}
    	
    //================================== Xóa Wallet ========================================  
    public TransactionHistoryResponseEntity deleteTransactionHistory(int transactionHistoryID) throws CrudException {
    	TransactionHistoryDTO dto = transactionRepo.findById(transactionHistoryID).orElse(null);
    	if(dto == null)
        	throw new StaffNotFoundByID();
    	transactionRepo.deleteById(transactionHistoryID);
        return TransactionHistoryResponseEntity.fromTransactionHistoryDTO(dto);
    }
}
