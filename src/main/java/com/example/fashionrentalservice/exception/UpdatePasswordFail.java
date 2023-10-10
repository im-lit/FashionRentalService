package com.example.fashionrentalservice.exception;

import org.springframework.http.HttpStatus;

import com.example.fashionrentalservice.exception.handlers.CrudException;

public class UpdatePasswordFail extends CrudException {
	public  UpdatePasswordFail() {
		// TODO Auto-generated constructor stub
		super("New password cannot be the same as old", HttpStatus.OK);
	}
}
	