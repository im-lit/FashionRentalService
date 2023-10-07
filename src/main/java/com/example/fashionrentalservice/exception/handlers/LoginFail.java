package com.example.fashionrentalservice.exception.handlers;

import org.springframework.http.HttpStatus;

public class LoginFail extends CrudException {
	public LoginFail() {
		super("Login Fail", HttpStatus.OK);
	}
	
}
