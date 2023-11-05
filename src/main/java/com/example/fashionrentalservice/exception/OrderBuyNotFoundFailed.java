package com.example.fashionrentalservice.exception;

import org.springframework.http.HttpStatus;

import com.example.fashionrentalservice.exception.handlers.CrudException;

public class OrderBuyNotFoundFailed extends CrudException {
	public OrderBuyNotFoundFailed() {
		super("OrderBuy not found by ID", HttpStatus.BAD_REQUEST);
	}
	
}
