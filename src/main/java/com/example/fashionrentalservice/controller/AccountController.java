
package com.example.fashionrentalservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.fashionrentalservice.service.AccountService;

import lombok.RequiredArgsConstructor;
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
	@Autowired
	private AccountService accService;
	
	@PostMapping("/login")
	private ResponseEntity checkLoginCus(@RequestParam String email, @RequestParam String password) {		
		return ResponseEntity.ok().body(accService.login(email, password));
	}
	
	
//	@PostMapping("/loginCus")
//	private ResponseEntity checkLoginCus(@RequestParam String email, @RequestParam String password) {		
//		return ResponseEntity.ok().body(accService.loginCus(email, password));
//	}
//	@PostMapping("/loginPo")
//	private ResponseEntity checkLoginPo(@RequestParam String email, @RequestParam String password) {		
//		return ResponseEntity.ok().body(accService.loginPO(email, password));
//	}
	
	@GetMapping("/getall")
	private ResponseEntity getAllAccount() {
		return ResponseEntity.ok().body(accService.getAllAccount());
	}
}