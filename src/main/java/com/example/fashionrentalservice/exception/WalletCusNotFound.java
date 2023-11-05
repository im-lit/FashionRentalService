package com.example.fashionrentalservice.exception;

import org.springframework.http.HttpStatus;

import com.example.fashionrentalservice.exception.handlers.CrudException;

public class WalletCusNotFound extends CrudException {
	public WalletCusNotFound() {
		super("Wallet Customer not found ", HttpStatus.BAD_REQUEST);
	}
	
}
