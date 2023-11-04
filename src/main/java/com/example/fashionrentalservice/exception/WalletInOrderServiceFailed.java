package com.example.fashionrentalservice.exception;

import org.springframework.http.HttpStatus;

import com.example.fashionrentalservice.exception.handlers.CrudException;

public class WalletInOrderServiceFailed extends CrudException {
	public WalletInOrderServiceFailed() {
		super("Wallet failed in OrderService", HttpStatus.BAD_REQUEST);
	}
	
}
