	
package com.example.fashionrentalservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.service.StaffRequestedService;

import lombok.RequiredArgsConstructor;
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/staffrequested")
public class StaffRequestedController {
	@Autowired
	private StaffRequestedService staffRequestedService;
	
	//================================== Tạo Request ========================================	
	@PostMapping
	private ResponseEntity createRequest(@RequestParam int requestAddingProductID, @RequestParam int staffID ) throws CrudException {		
		return ResponseEntity.ok().body(staffRequestedService.create(requestAddingProductID,staffID));
	}
	
	//================================== Lấy tất cả requested ========================================	
	@GetMapping("/getall")
	private ResponseEntity getAllStaffRequested() {
		return ResponseEntity.ok().body(staffRequestedService.getAllStaffRequested());
	}
	
	@GetMapping("/{staffID}")
	private ResponseEntity getAllStaffRequestedByStaffID(@PathVariable int staffID) {
		return ResponseEntity.ok().body(staffRequestedService.getAllApprovedStaffRequestedByStaffID(staffID));
	}
}
	

	