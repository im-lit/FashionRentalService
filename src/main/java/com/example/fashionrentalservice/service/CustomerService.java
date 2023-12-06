package com.example.fashionrentalservice.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fashionrentalservice.exception.AccountIsRegisted;
import com.example.fashionrentalservice.exception.CusNotFoundByID;
import com.example.fashionrentalservice.exception.PendingMoneyNegative;
import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.dto.account.AccountDTO;
import com.example.fashionrentalservice.model.dto.account.CustomerDTO;
import com.example.fashionrentalservice.model.request.CustomerRequestEntity;
import com.example.fashionrentalservice.model.request.CustomerUpdateRequestEntity;
import com.example.fashionrentalservice.model.response.CustomerResponseEntity;
import com.example.fashionrentalservice.repositories.AccountRepository;
import com.example.fashionrentalservice.repositories.CustomerRepository;
@Service
public class CustomerService {

	@Autowired
	private CustomerRepository cusRepo;
	
	@Autowired
	private AccountRepository accRepo;
	private static final String BLANK_PATTERN = "^\\S.*$";
	
	//================================== Lay tat ca Customer ========================================
	public List<CustomerResponseEntity> getAllCustomer() {
		return  cusRepo.findAll().stream()
                .map(CustomerResponseEntity::fromCustomerDTO)
                .collect(Collectors.toList());
		
	}
	
	//================================== Tao Customer========================================
    public CustomerResponseEntity createCustomer(CustomerRequestEntity entity) throws CrudException{
    	AccountDTO check = accRepo.findById(entity.getAccountID()).orElse(null);
    	if(check==null) {
    		throw new PendingMoneyNegative("Cannot found AccountID");
    	}
    	if(!isValidStringNotBlank(entity.getFullName())) {
       		throw new PendingMoneyNegative("FullName Cannot blank");
       	}
    	if(!isValidStringNotBlank(entity.getPhone())) {
       		throw new PendingMoneyNegative("Phone Cannot blank");
       	}
    	if(check.getStaffDTO() == null && check.getProductOwnerDTO() == null && check.getCustomerDTO() == null) {
            CustomerDTO dto = CustomerDTO.builder()
                    .fullName(entity.getFullName())
                    .phone(entity.getPhone())
                    .sex(entity.isSex())
                    .avatarUrl(entity.getAvatarUrl())
                    .accountDTO(accRepo.findById(entity.getAccountID()).orElseThrow())
                    .build();
            return CustomerResponseEntity.fromCustomerDTO(cusRepo.save(dto));
    	}
    	throw new AccountIsRegisted();
        //
       // if(entity.getPhone()!=null)
     //	   throw new CreateCustomerFail();           
     }
    private boolean isValidStringNotBlank(String string) {
        Pattern pattern = Pattern.compile(BLANK_PATTERN);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }
    
    
    
    
    
  //================================== Update Customer========================================
    public CustomerResponseEntity updateCustomer(int customerID,CustomerUpdateRequestEntity entity) throws CrudException {
    	CustomerDTO dto = cusRepo.findById(customerID).orElse(null);
		if(dto==null) {
			throw new PendingMoneyNegative("Cannot find Customer ID!");
		}
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
    public CustomerResponseEntity deleteExistedCustomer(int id) throws CrudException {
    	CustomerDTO dto = cusRepo.findById(id).orElse(null);
		if(dto==null) {
			throw new PendingMoneyNegative("Cannot find Customer ID!");
		}
        cusRepo.deleteById(id);

        return CustomerResponseEntity.fromCustomerDTO(dto);
    }	
	
	
}
