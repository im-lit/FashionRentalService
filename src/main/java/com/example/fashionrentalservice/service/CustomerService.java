package com.example.fashionrentalservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fashionrentalservice.exception.AccNotFoundByID;
import com.example.fashionrentalservice.exception.CreateCustomerFail;
import com.example.fashionrentalservice.exception.CusNotFoundByID;
import com.example.fashionrentalservice.exception.EmailExisted;
import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.dto.account.AccountDTO;
import com.example.fashionrentalservice.model.dto.account.CustomerDTO;
import com.example.fashionrentalservice.model.dto.account.ProductOwnerDTO;
import com.example.fashionrentalservice.model.request.CustomerRequestEntity;
import com.example.fashionrentalservice.model.request.CustomerUpdateRequestEntity;
import com.example.fashionrentalservice.model.response.AccountResponseEntity;
import com.example.fashionrentalservice.model.response.CustomerResponseEntity;
import com.example.fashionrentalservice.model.response.POResponseEntity;
import com.example.fashionrentalservice.repositories.AccountRepository;
import com.example.fashionrentalservice.repositories.CustomerRepository;
@Service
public class CustomerService {

	@Autowired
	private CustomerRepository cusRepo;
	
	@Autowired
	private AccountRepository accRepo;
	
	//================================== Lay tat ca Customer ========================================
	public List<CustomerResponseEntity> getAllCustomer() {
		return  cusRepo.findAll().stream()
                .map(CustomerResponseEntity::fromCustomerDTO)
                .collect(Collectors.toList());
		
	}
	
	//================================== Tao Customer========================================
    public CustomerResponseEntity createCustomer(CustomerRequestEntity entity) throws CrudException{
    	
        CustomerDTO dto = CustomerDTO.builder()
                .fullName(entity.getFullName())
                .phone(entity.getPhone())
                .balance(entity.getBalance())
                .sex(entity.isSex())
                .status(true)
                .avatarUrl(entity.getAvatarUrl())
                .accountDTO(accRepo.findById(entity.getAccountID()).orElseThrow())
                .build();
        //
       // if(entity.getPhone()!=null)
     //	   throw new CreateCustomerFail();
        
        return CustomerResponseEntity.fromCustomerDTO(cusRepo.save(dto));
     }
    
    
    
    
    
  //================================== Update Customer========================================
    public CustomerResponseEntity updateCustomer(int customerID,CustomerUpdateRequestEntity entity) {
        CustomerDTO dto = cusRepo.findById(customerID).orElseThrow();
        dto.setAvatarUrl(entity.getAvatarUrl());
        dto.setFullName(entity.getFullName());
        dto.setPhone(entity.getPhone());
        dto.setSex(entity.isSex());
        ;
        return CustomerResponseEntity.fromCustomerDTO(cusRepo.save(dto));
    }
    
    //================================== Lấy Customer bởi ID========================================    
	public CustomerResponseEntity getCustomerByID(int customerID) throws CrudException {
		CustomerDTO dto = cusRepo.findById(customerID).orElse(null);
		if(dto==null) 
			throw new CusNotFoundByID();
		return CustomerResponseEntity.fromCustomerDTO(dto);
		}
	
	//================================== Xóa ========================================	
    public CustomerResponseEntity deleteExistedCustomer(int id) {
        CustomerDTO dto = cusRepo.findById(id).orElseThrow();
        cusRepo.deleteById(id);

        return CustomerResponseEntity.fromCustomerDTO(dto);
    }	
	
	
}