package com.example.fashionrentalservice.exception;

import org.springframework.http.HttpStatus;

import com.example.fashionrentalservice.exception.handlers.CrudException;

public class OrderBuyIDNotFound extends CrudException {
	public OrderBuyIDNotFound() {
		super("Order Buy ID Not Found", HttpStatus.BAD_REQUEST);
	}
	
}
