package com.example.fashionrentalservice.exception;

import org.springframework.http.HttpStatus;

import com.example.fashionrentalservice.exception.handlers.CrudException;

public class ProductNotAvailable extends CrudException{

	public ProductNotAvailable() {
		super("The Product Is Not Available", HttpStatus.BAD_REQUEST);
		// TODO Auto-generated constructor stub
	}

}
