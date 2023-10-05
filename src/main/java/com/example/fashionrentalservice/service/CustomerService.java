package com.example.fashionrentalservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fashionrentalservice.model.response.CustomerResponseEntity;
import com.example.fashionrentalservice.repositories.CustomerRepository;
@Service
public class CustomerService {

	@Autowired
	private CustomerRepository cusRepo;
	
	
	public List<CustomerResponseEntity> getAllAccount() {
		return  cusRepo.findAll().stream()
                .map(CustomerResponseEntity::fromCustomerDTO)
                .collect(Collectors.toList());
		
	}
}
