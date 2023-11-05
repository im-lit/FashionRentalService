package com.example.fashionrentalservice.exception;

import org.springframework.http.HttpStatus;

import com.example.fashionrentalservice.exception.handlers.CrudException;

public class PendingMoneyNegative extends CrudException {
	public PendingMoneyNegative() {
		super("Pending Money cannot be a negative number", HttpStatus.BAD_REQUEST);
	}
	
}
