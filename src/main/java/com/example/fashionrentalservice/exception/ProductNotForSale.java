package com.example.fashionrentalservice.exception;

import org.springframework.http.HttpStatus;

import com.example.fashionrentalservice.exception.handlers.CrudException;

public class ProductNotForSale extends CrudException{

	public ProductNotForSale() {
		super("The Product is not for Sale", HttpStatus.BAD_REQUEST);
		// TODO Auto-generated constructor stub
	}

}
