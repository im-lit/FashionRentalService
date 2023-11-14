package com.example.fashionrentalservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fashionrentalservice.exception.AccountIsRegisted;
import com.example.fashionrentalservice.exception.PONotFoundByID;
import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.dto.account.AccountDTO;
import com.example.fashionrentalservice.model.dto.account.ProductOwnerDTO;
import com.example.fashionrentalservice.model.request.PORequestEntity;
import com.example.fashionrentalservice.model.request.POUpdateRequestEntity;
import com.example.fashionrentalservice.model.request.POUpdateTokenAndShopIDResquestEntity;
import com.example.fashionrentalservice.model.response.POResponseEntity;
import com.example.fashionrentalservice.repositories.AccountRepository;
import com.example.fashionrentalservice.repositories.ProductOwnerRepository;

@Service
public class ProductOwnerService {

	@Autowired
	private ProductOwnerRepository poRepo;
	
	@Autowired
	private AccountRepository accRepo;
	
//	------------------ Lay tat ca ProductOwner-----------
	public List<POResponseEntity> getAllProductOwner() {
		return  poRepo.findAll().stream()
                .map(POResponseEntity::fromPODTO)
                .collect(Collectors.toList());
		
	}
	 //================================== Tạo PO========================================
    public POResponseEntity createProductOwner(PORequestEntity entity) throws CrudException{
    	AccountDTO check = accRepo.findById(entity.getAccountID()).orElseThrow();
    	if(check.getCustomerDTO() == null && check.getStaffDTO() == null && check.getProductOwnerDTO() == null) {
        ProductOwnerDTO dto = ProductOwnerDTO.builder()
                .fullName(entity.getFullName())
                .phone(entity.getPhone())
                .avatarUrl(entity.getAvatarUrl())
                .address(entity.getAddress())
                .accountDTO(accRepo.findById(entity.getAccountID()).orElseThrow())
                .build();
        return POResponseEntity.fromPODTO(poRepo.save(dto));
    	} 
    	throw new AccountIsRegisted();
    }
    //================================== Update PO bởi ID========================================
    public POResponseEntity updateProductOwner(int productownerID,POUpdateRequestEntity entity) {
    	ProductOwnerDTO dto = poRepo.findById(productownerID).orElseThrow();
    	dto.setAvatarUrl(entity.getAvatarUrl());
    	dto.setPhone(entity.getPhone());
    	dto.setFullName(entity.getFullName());
    	dto.setAddress(entity.getAddress());
    	
    	return POResponseEntity.fromPODTO(poRepo.save(dto));
    }
    
    public POResponseEntity updateProductOwnerTokenAndShopID(int productownerID,POUpdateTokenAndShopIDResquestEntity entity) {
    	ProductOwnerDTO dto = poRepo.findById(productownerID).orElseThrow();
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
    public POResponseEntity deleteExistedProductOwner(int id) {
        ProductOwnerDTO dto = poRepo.findById(id).orElseThrow();
        poRepo.deleteById(id);

        return POResponseEntity.fromPODTO(dto);
    }
	
		
		
		
		
		
		
	
}
