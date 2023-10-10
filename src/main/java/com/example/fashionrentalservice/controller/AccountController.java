
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


import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.dto.account.AccountDTO;
import com.example.fashionrentalservice.model.response.AccountResponseEntity;

import com.example.fashionrentalservice.model.request.AccountRequestEntity;

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
	private ResponseEntity checkLogin(@RequestParam String email, @RequestParam String password) throws CrudException {	
		
		return ResponseEntity.ok().body(accService.login(email, password));
	}
	
	
	@PostMapping("/create")
	private ResponseEntity createNewAccount(@RequestBody AccountRequestEntity entity ) throws CrudException {		
		return ResponseEntity.ok().body(accService.createNewAccount(entity));
	}
	
	@PutMapping("/update")
	private ResponseEntity updatePasswordAccount(@RequestParam int accountID,@RequestParam String password) throws CrudException {		
		return ResponseEntity.ok().body(accService.updatePasswordAccount(accountID,password));
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
	
	@GetMapping("/getaccount")
	private ResponseEntity getAccountByID(@RequestParam int accountID) throws CrudException{
		return ResponseEntity.ok().body(accService.getAccountByID(accountID));
	}
}