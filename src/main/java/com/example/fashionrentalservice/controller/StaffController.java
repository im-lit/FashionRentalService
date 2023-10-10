package com.example.fashionrentalservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.fashionrentalservice.model.request.CustomerRequestEntity;
import com.example.fashionrentalservice.model.request.CustomerUpdateRequestEntity;
import com.example.fashionrentalservice.model.request.StaffRequestEntity;
import com.example.fashionrentalservice.service.StaffService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/staff")
public class StaffController {
	@Autowired
	private StaffService staffService;
	
//================================== Lay tat ca Customer ========================================
	@GetMapping("/getstaffs")
	private ResponseEntity getAllStaff() {
		return ResponseEntity.ok().body(staffService.getAllStaff());
	}
	
	@PostMapping("/createstaff")
	private ResponseEntity createStaff(@RequestBody StaffRequestEntity entity) {
		return ResponseEntity.ok().body(staffService.createStaff(entity));
	}
	
	@PutMapping
	private ResponseEntity updateStatusStaff(@RequestParam int staffID,@RequestParam boolean status) {
		return ResponseEntity.ok().body(staffService.updateStatusStaff(staffID,status));
	}
	
	@GetMapping("/getstaff")
	private ResponseEntity getStaffByID(@RequestParam int staffID) {
		return ResponseEntity.ok().body(staffService.getStaffByID(staffID));
	}
}
