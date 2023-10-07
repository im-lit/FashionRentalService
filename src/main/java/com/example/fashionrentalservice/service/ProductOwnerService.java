package com.example.fashionrentalservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fashionrentalservice.model.dto.account.ProductOwnerDTO;
import com.example.fashionrentalservice.model.request.PORequestEntity;
import com.example.fashionrentalservice.model.request.POUpdateRequestEntity;
import com.example.fashionrentalservice.model.response.POResponseEntity;
import com.example.fashionrentalservice.repositories.ProductOwnerRepository;

@Service
public class ProductOwnerService {

	@Autowired
	private ProductOwnerRepository poRepo;
	
//	------------------ Lay tat ca ProductOwner-----------
	public List<POResponseEntity> getAllProductOwner() {
		return  poRepo.findAll().stream()
                .map(POResponseEntity::fromPODTO)
                .collect(Collectors.toList());
		
	}
	
    public POResponseEntity createProductOwner(PORequestEntity entity) {
        ProductOwnerDTO dto = ProductOwnerDTO.builder()
                .fullName(entity.getFullName())
                .phone(entity.getPhone())
                .balance(entity.getBalance())
                .status(entity.isStatus())
                .avatarUrl(entity.getAvatarUrl())
                .address(entity.getAddress())
                .build();
        
        return POResponseEntity.fromPODTO(poRepo.save(dto));
    }
    
    public POResponseEntity updateProductOwner(int productownerID,POUpdateRequestEntity entity) {
    	ProductOwnerDTO dto = poRepo.findById(productownerID).orElseThrow();
    	dto.setAvatarUrl(entity.getAvatarUrl());
    	dto.setPhone(entity.getPhone());
    	dto.setFullName(entity.getFullName());
    	dto.setAddress(entity.getAddress());
    	
    	return POResponseEntity.fromPODTO(poRepo.save(dto));
    }
}
