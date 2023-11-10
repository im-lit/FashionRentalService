package com.example.fashionrentalservice.exception;

import org.springframework.http.HttpStatus;

import com.example.fashionrentalservice.exception.handlers.CrudException;

public class ProductIsSoldOut extends CrudException{
	 public ProductIsSoldOut(String msg) {
		// TODO Auto-generated constructor stub
		super(msg, HttpStatus.BAD_REQUEST);
}
}