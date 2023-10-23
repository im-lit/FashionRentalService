package com.example.fashionrentalservice.exception;

import org.springframework.http.HttpStatus;

import com.example.fashionrentalservice.exception.handlers.CrudException;

public class CusNotFoundByID extends CrudException{
	public CusNotFoundByID() {
		super("Cannot Found Customer By ID",HttpStatus.BAD_REQUEST );
		// TODO Auto-generated constructor stub
	}
}
