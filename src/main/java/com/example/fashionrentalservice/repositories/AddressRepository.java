package com.example.fashionrentalservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.fashionrentalservice.model.dto.account.AddressDTO;

public interface AddressRepository extends JpaRepository<AddressDTO, Integer>{
	@Query("select dto from AddressDTO dto where dto.customerDTO.customerID = ?1 ")
	List<AddressDTO> findAllAddressByCustomerID(int customerID);

}
