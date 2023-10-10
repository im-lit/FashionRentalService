package com.example.fashionrentalservice.exception;

import org.springframework.http.HttpStatus;

import com.example.fashionrentalservice.exception.handlers.CrudException;

public class CreateCustomerFail extends CrudException {
	public CreateCustomerFail() {
		super("Created Fail By Email Already Existed", HttpStatus.OK);
	}
}
