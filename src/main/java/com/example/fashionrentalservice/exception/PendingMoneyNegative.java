package com.example.fashionrentalservice.exception;

import org.springframework.http.HttpStatus;

import com.example.fashionrentalservice.exception.handlers.CrudException;

public class PendingMoneyNegative extends CrudException {
	public PendingMoneyNegative(String msg) {
		super(msg, HttpStatus.BAD_REQUEST);
	}
	
}
