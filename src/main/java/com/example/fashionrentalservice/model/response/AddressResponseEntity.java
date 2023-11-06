package com.example.fashionrentalservice.model.response;

import com.example.fashionrentalservice.model.dto.account.AddressDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseEntity {
	
	
	private int addressID;
	
	private String addressDescription;
	
	private int customerID;

	public static AddressResponseEntity fromAddressDTO(AddressDTO dto) {
        return AddressResponseEntity.builder()
                .addressID(dto.getAddressID())
                .addressDescription(dto.getAddressDescription())
                .customerID(dto.getCustomerDTO().getCustomerID())
                .build();   
	}
}
