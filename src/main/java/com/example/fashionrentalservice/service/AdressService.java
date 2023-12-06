package com.example.fashionrentalservice.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fashionrentalservice.exception.CusNotFoundByID;
import com.example.fashionrentalservice.exception.PendingMoneyNegative;
import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.dto.account.AddressDTO;
import com.example.fashionrentalservice.model.dto.account.CustomerDTO;
import com.example.fashionrentalservice.model.request.AddressRequestEntity;
import com.example.fashionrentalservice.model.response.AddressResponseEntity;
import com.example.fashionrentalservice.repositories.AddressRepository;
import com.example.fashionrentalservice.repositories.CustomerRepository;

@Service
public class AdressService {

	@Autowired
	private CustomerRepository cusRepo;
	
	@Autowired
	private AddressRepository addressRepo;
	
	private static final String BLANK_PATTERN = "^\\S.*$";



//================================== Tạo mới Account ========================================
    public AddressResponseEntity createNewAddress(AddressRequestEntity entity) throws CrudException{
    	
    	CustomerDTO check = cusRepo.findById(entity.getCustomerID()).orElse(null);
    	if(check == null)
    		throw new CusNotFoundByID();
    	
    	if(!isValidStringNotBlank(entity.getAddressDescription())) {
       		throw new PendingMoneyNegative("Address Description Cannot blank");
       	}

    	AddressDTO dto = AddressDTO.builder()
                .addressDescription(entity.getAddressDescription())
                .customerDTO(check)
                .build();
       
        return AddressResponseEntity.fromAddressDTO(addressRepo.save(dto));
    }


//================================== Lay tat ca address by CustomerID========================================
	public List<AddressDTO> getAllAddressByCustomerID(int customerID) {
		List<AddressDTO> list = addressRepo.findAllAddressByCustomerID(customerID);	
		return list;
	}

//================================== Xóa Address bởi AddressID ========================================
    public AddressDTO deleteAddress(int addressid) {
    	AddressDTO dto = addressRepo.findById(addressid).orElse(null);
    	addressRepo.delete(dto);

        return dto;
    }
    private boolean isValidStringNotBlank(String string) {
        Pattern pattern = Pattern.compile(BLANK_PATTERN);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }
}
	
	
