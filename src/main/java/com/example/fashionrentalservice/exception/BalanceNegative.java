package com.example.fashionrentalservice.exception;

import org.springframework.http.HttpStatus;

import com.example.fashionrentalservice.exception.handlers.CrudException;

public class BalanceNegative extends CrudException {
	public BalanceNegative() {
		super("Balance cannot be a negative number", HttpStatus.BAD_REQUEST);
	}
	
}
