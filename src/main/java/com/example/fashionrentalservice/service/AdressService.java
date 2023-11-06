package com.example.fashionrentalservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fashionrentalservice.exception.CusNotFoundByID;
import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.dto.account.AddressDTO;
import com.example.fashionrentalservice.model.dto.account.CustomerDTO;
import com.example.fashionrentalservice.repositories.AddressRepository;
import com.example.fashionrentalservice.repositories.CustomerRepository;

@Service
public class AdressService {

	@Autowired
	private CustomerRepository cusRepo;
	
	@Autowired
	private AddressRepository addressRepo;
	
	



//================================== Tạo mới Account ========================================
    public AddressDTO createNewAddress(String address, int customerID) throws CrudException{
    	
    	CustomerDTO check = cusRepo.findById(customerID).orElse(null);
    	if(check == null)
    		throw new CusNotFoundByID();
    	AddressDTO dto = AddressDTO.builder()
                .addressDescription(address)
                .customerDTO(check)
                .build();
       
        return addressRepo.save(dto);
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
}
	
	
