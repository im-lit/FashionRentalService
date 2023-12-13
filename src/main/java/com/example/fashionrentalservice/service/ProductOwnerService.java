package com.example.fashionrentalservice.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fashionrentalservice.exception.AccountIsRegisted;
import com.example.fashionrentalservice.exception.PONotFoundByID;
import com.example.fashionrentalservice.exception.PendingMoneyNegative;
import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.dto.account.AccountDTO;
import com.example.fashionrentalservice.model.dto.account.ProductOwnerDTO;
import com.example.fashionrentalservice.model.request.PORequestEntity;
import com.example.fashionrentalservice.model.request.POUpdateRequestEntity;
import com.example.fashionrentalservice.model.request.POUpdateTokenAndShopIDResquestEntity;
import com.example.fashionrentalservice.model.request.WalletRequestEntity;
import com.example.fashionrentalservice.model.response.POResponseEntity;
import com.example.fashionrentalservice.model.response.WalletResponseEntity;
import com.example.fashionrentalservice.repositories.AccountRepository;
import com.example.fashionrentalservice.repositories.ProductOwnerRepository;

@Service
public class ProductOwnerService {

	@Autowired
	private ProductOwnerRepository poRepo;
	
	@Autowired
	private AccountRepository accRepo;
	
	@Autowired
	private WalletService wallService;
	
	private static final String BLANK_PATTERN = "^\\S.*$";
//	------------------ Lay tat ca ProductOwner-----------
	public List<POResponseEntity> getAllProductOwner() {
		return  poRepo.findAll().stream()
                .map(POResponseEntity::fromPODTO)
                .collect(Collectors.toList());
		
	}
	 //================================== Tạo PO========================================
    public POResponseEntity createProductOwner(PORequestEntity entity) throws CrudException{
    	AccountDTO check = accRepo.findById(entity.getAccountID()).orElse(null);
    	
		if(check==null) {
			throw new PendingMoneyNegative("Cannot find PO ID!");
		}
		if(!isValidStringNotBlank(entity.getFullName())) {
       		throw new PendingMoneyNegative("FullName Cannot blank");
       	}
    	if(!isValidStringNotBlank(entity.getPhone())) {
       		throw new PendingMoneyNegative("Phone Cannot blank");
       	}
    	if(!isValidStringNotBlank(entity.getAddress())) {
       		throw new PendingMoneyNegative("FullName Cannot blank");
       	}
    	
    	int reputationPoint=0;
    	if(check.getCustomerDTO() == null && check.getStaffDTO() == null && check.getProductOwnerDTO() == null) {
    	List<ProductOwnerDTO> list = poRepo.findAll();
        	for (ProductOwnerDTO x : list) {
        		if(x.getPhone().equalsIgnoreCase(entity.getPhone()))
    				throw new PendingMoneyNegative("This phone number is used by someone else");
    	}
        ProductOwnerDTO dto = ProductOwnerDTO.builder()
                .fullName(entity.getFullName())
                .phone(entity.getPhone())
                .avatarUrl(entity.getAvatarUrl())
                .address(entity.getAddress())
                .reputationPoint(reputationPoint)
                .accountDTO(accRepo.findById(entity.getAccountID()).orElseThrow())
                .build();
        
        WalletRequestEntity request = WalletRequestEntity.builder()
        								.accountID(entity.getAccountID())
        								.balance(0)
        								.build();
        WalletResponseEntity checkWallet = wallService.createWallet(request);
        if(checkWallet == null)
        	throw new PendingMoneyNegative("Create Wallet for PO failed!");
        return POResponseEntity.fromPODTO(poRepo.save(dto));
    	} 
    	throw new AccountIsRegisted();
    }
    //================================== Update PO bởi ID========================================
    public POResponseEntity updateProductOwner(int productownerID,POUpdateRequestEntity entity) throws CrudException {
    	ProductOwnerDTO dto = poRepo.findById(productownerID).orElse(null);
    	List<ProductOwnerDTO> list = poRepo.findAll();

    	if(dto==null) 
			throw new PendingMoneyNegative("Cannot find PO ID!");
		
    	for (ProductOwnerDTO x : list) {
    		if(x.getPhone().equalsIgnoreCase(entity.getPhone()))
				throw new PendingMoneyNegative("This phone number is used by someone else");
    	}
    	dto.setAvatarUrl(entity.getAvatarUrl());
    	dto.setPhone(entity.getPhone());
    	dto.setFullName(entity.getFullName());
    	dto.setAddress(entity.getAddress());
    	
    	return POResponseEntity.fromPODTO(poRepo.save(dto));
    }
    
    public POResponseEntity votePOReputationPoint(int productownerID) throws CrudException {
    	ProductOwnerDTO dto = poRepo.findById(productownerID).orElse(null);
    	if(dto==null) {
			throw new PendingMoneyNegative("Cannot find PO ID!");
		}
    	dto.setReputationPoint(dto.getReputationPoint()+1);
    	return POResponseEntity.fromPODTO(poRepo.save(dto));
    }
    public POResponseEntity unVotePOReputationPoint(int productownerID) throws CrudException {
    	ProductOwnerDTO dto = poRepo.findById(productownerID).orElse(null);
    	if(dto==null) {
			throw new PendingMoneyNegative("Cannot find PO ID!");
		}
    	dto.setReputationPoint(dto.getReputationPoint()-1);
    	return POResponseEntity.fromPODTO(poRepo.save(dto));
    }
    
    public POResponseEntity updateProductOwnerTokenAndShopID(int productownerID,POUpdateTokenAndShopIDResquestEntity entity) throws CrudException {
    	ProductOwnerDTO dto = poRepo.findById(productownerID).orElse(null);
    	if(dto==null) {
			throw new PendingMoneyNegative("Cannot find PO ID!");
		}
    	dto.setPOShopID(entity.getPOShopID());
    	dto.setPOToken(entity.getPOToken());
    	
    	return POResponseEntity.fromPODTO(poRepo.save(dto));
    }
    
    
    //================================== Lấy ProductOwner bởi ID========================================    
	public POResponseEntity getPOByID(int productownerID) throws CrudException{
		ProductOwnerDTO dto = poRepo.findById(productownerID).orElse(null);
		if(dto==null) 
			throw new PONotFoundByID();
		return POResponseEntity.fromPODTO(dto);
		}
	
	 //================================== Xóa ProductOwner ========================================
    public POResponseEntity deleteExistedProductOwner(int id) throws CrudException {
    	ProductOwnerDTO dto = poRepo.findById(id).orElse(null);
    	if(dto==null) {
			throw new PendingMoneyNegative("Cannot find PO ID!");
		}
        poRepo.deleteById(id);

        return POResponseEntity.fromPODTO(dto);
    }
    
    private boolean isValidStringNotBlank(String string) {
        Pattern pattern = Pattern.compile(BLANK_PATTERN);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }
	
		
		
		
		
		
		
	
}
