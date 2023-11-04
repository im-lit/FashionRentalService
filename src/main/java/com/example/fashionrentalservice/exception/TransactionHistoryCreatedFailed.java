package com.example.fashionrentalservice.exception;

import org.springframework.http.HttpStatus;

import com.example.fashionrentalservice.exception.handlers.CrudException;

public class TransactionHistoryCreatedFailed extends CrudException {
	public TransactionHistoryCreatedFailed() {
		super("Created Buy Transaction History Failed", HttpStatus.BAD_REQUEST);
	}
	
}
