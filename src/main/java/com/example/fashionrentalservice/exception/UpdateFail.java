package com.example.fashionrentalservice.exception;

import org.springframework.http.HttpStatus;

import com.example.fashionrentalservice.exception.handlers.CrudException;

public class UpdateFail extends CrudException {
	public  UpdateFail() {
		// TODO Auto-generated constructor stub
		super("Update Fail", HttpStatus.OK);
	}
}
	