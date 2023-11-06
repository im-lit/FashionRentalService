	
package com.example.fashionrentalservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.service.AdressService;

import lombok.RequiredArgsConstructor;
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/address")
public class AddressController {
	@Autowired
	private AdressService adressService;

	
	
	//================================== Tạo mới Account ========================================	
	@PostMapping
	private ResponseEntity createNewAddress(@RequestParam String address, @RequestParam int customerID ) throws CrudException {		
		return ResponseEntity.ok().body(adressService.createNewAddress(address, customerID));
	}
	
	
	
	//================================== Lấy tất cả Address ========================================	
	@GetMapping("/{customerID}")
	private ResponseEntity getAllAddressByCustomerID(@PathVariable int customerID) {
		return ResponseEntity.ok().body(adressService.getAllAddressByCustomerID(customerID));
	}
	
	
	//================================== Xóa ========================================	
    @DeleteMapping
    private ResponseEntity deleteAddress(@RequestParam int addressID) {
        return ResponseEntity.ok().body(adressService.deleteAddress(addressID));
    }
}