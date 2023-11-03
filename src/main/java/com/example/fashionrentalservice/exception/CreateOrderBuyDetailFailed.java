package com.example.fashionrentalservice.exception;

import org.springframework.http.HttpStatus;

import com.example.fashionrentalservice.exception.handlers.CrudException;

public class CreateOrderBuyDetailFailed extends CrudException {
	public CreateOrderBuyDetailFailed() {
		super("Create Order Detail Failed", HttpStatus.BAD_REQUEST);
	}
	
}
