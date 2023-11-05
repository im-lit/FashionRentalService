package com.example.fashionrentalservice.exception;

import org.springframework.http.HttpStatus;

import com.example.fashionrentalservice.exception.handlers.CrudException;

public class WalletPoNotFound extends CrudException {
	public WalletPoNotFound() {
		super("Wallet ProductOwner not found ", HttpStatus.BAD_REQUEST);
	}
	
}
