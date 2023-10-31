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
import com.example.fashionrentalservice.model.request.TransactionHistoryRequestEntity;
import com.example.fashionrentalservice.model.request.WalletRequestEntity;
import com.example.fashionrentalservice.service.TransactionHistoryService;
import com.example.fashionrentalservice.service.WalletService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/trans")
public class AccountTransactionHistoryController {
	@Autowired
	private TransactionHistoryService transService;
	
	
	@GetMapping("/{accountID}")
	private ResponseEntity getAllTransactionByAccountID(@PathVariable int accountID) throws CrudException {
		return ResponseEntity.ok().body(transService.getAllTransactionByAccountID(accountID));
	}
	
	
	@PostMapping
	private ResponseEntity createTransactionHistory(@RequestBody TransactionHistoryRequestEntity entity) throws CrudException {
		return ResponseEntity.ok().body(transService.createTransactionHistory(entity));
	}
	
    @DeleteMapping()
    private ResponseEntity deleteTransactionHistory(@RequestParam int transactionID) throws CrudException {
        return ResponseEntity.ok().body(transService.deleteTransactionHistory(transactionID));
    }
}
