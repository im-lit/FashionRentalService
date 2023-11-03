package com.example.fashionrentalservice.exception;

import org.springframework.http.HttpStatus;

import com.example.fashionrentalservice.exception.handlers.CrudException;

public class CreateOrderFailed extends CrudException {
	public CreateOrderFailed() {
		super("Create Order Failed", HttpStatus.BAD_REQUEST);
	}
	
}
