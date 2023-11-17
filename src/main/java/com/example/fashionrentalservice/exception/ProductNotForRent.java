package com.example.fashionrentalservice.exception;

import org.springframework.http.HttpStatus;

import com.example.fashionrentalservice.exception.handlers.CrudException;

public class ProductNotForRent extends CrudException{

	public ProductNotForRent() {
		super("The Product is not for Sell", HttpStatus.BAD_REQUEST);
		// TODO Auto-generated constructor stub
	}

}
