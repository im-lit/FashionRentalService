package com.example.fashionrentalservice.exception;

import org.springframework.http.HttpStatus;

import com.example.fashionrentalservice.exception.handlers.CrudException;

public class AccountIsRegisted extends CrudException{

	public AccountIsRegisted() {
		super("This Account had been registed",HttpStatus.BAD_REQUEST );
		// TODO Auto-generated constructor stub
	}

}
