package com.example.fashionrentalservice.exception;

import org.springframework.http.HttpStatus;

import com.example.fashionrentalservice.exception.handlers.CrudException;

public class EmailExisted extends CrudException {
	public EmailExisted() {
		super("Created Fail By Email Already Existed", HttpStatus.BAD_REQUEST);
	}
	
}
