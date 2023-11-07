package com.example.fashionrentalservice.exception;

import org.springframework.http.HttpStatus;

import com.example.fashionrentalservice.exception.handlers.CrudException;

public class ProductIsRented extends CrudException{

	public ProductIsRented(String productName) {
		super("The Product " + productName  + "is Rented on that date ", HttpStatus.BAD_REQUEST);
		// TODO Auto-generated constructor stub
	}

}
