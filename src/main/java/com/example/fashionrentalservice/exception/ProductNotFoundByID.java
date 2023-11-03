package com.example.fashionrentalservice.exception;

import org.springframework.http.HttpStatus;

import com.example.fashionrentalservice.exception.handlers.CrudException;

public class ProductNotFoundByID extends CrudException{
	public ProductNotFoundByID() {
		super("Cannot found Product By ID", HttpStatus.BAD_REQUEST);
	}
}
