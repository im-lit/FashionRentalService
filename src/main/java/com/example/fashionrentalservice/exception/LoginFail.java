package com.example.fashionrentalservice.exception;

import org.springframework.http.HttpStatus;

import com.example.fashionrentalservice.exception.handlers.CrudException;

public class LoginFail extends CrudException {
	public LoginFail() {
		super("Login Fail", HttpStatus.OK);
	}
	
}
