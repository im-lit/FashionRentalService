package com.example.fashionrentalservice.exception;

import org.springframework.http.HttpStatus;

import com.example.fashionrentalservice.exception.handlers.CrudException;

public class PONotFoundByID extends CrudException{

	public PONotFoundByID() {
		super("Cannot found ProductOwner By ID", HttpStatus.BAD_REQUEST);
		// TODO Auto-generated constructor stub
	}

}
