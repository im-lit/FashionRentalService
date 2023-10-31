package com.example.fashionrentalservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.request.WalletRequestEntity;
import com.example.fashionrentalservice.service.WalletService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/wallet")
public class WalletController {
	@Autowired
	private WalletService walletService;
	
	
	@GetMapping("/{accountID}")
	private ResponseEntity getWalletByAccountID(@PathVariable int accountID) throws CrudException {
		return ResponseEntity.ok().body(walletService.getWalletByAccountID(accountID));
	}
	
	
	@PostMapping
	private ResponseEntity createWallet(@RequestBody WalletRequestEntity entity) throws CrudException {
		return ResponseEntity.ok().body(walletService.createWallet(entity));
	}
	
	@PutMapping
	private ResponseEntity updateBalance(@RequestParam int walletID,@RequestParam double balance) throws CrudException {
		return ResponseEntity.ok().body(walletService.updateBalance(walletID,balance));
	}
	
    @DeleteMapping()
    private ResponseEntity deleteWallet(@RequestParam int walletID) throws CrudException {
        return ResponseEntity.ok().body(walletService.deleteWallet(walletID));
    }
}
