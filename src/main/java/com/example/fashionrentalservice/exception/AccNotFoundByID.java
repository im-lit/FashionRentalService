package com.example.fashionrentalservice.exception;

import org.springframework.http.HttpStatus;

import com.example.fashionrentalservice.exception.handlers.CrudException;

public class AccNotFoundByID extends CrudException{

	public AccNotFoundByID() {
		super("Cannot Found Account By ID",HttpStatus.OK );
		// TODO Auto-generated constructor stub
	}

}
