package com.example.fashionrentalservice.exception;

import org.springframework.http.HttpStatus;

import com.example.fashionrentalservice.exception.handlers.CrudException;

public class StaffNotFoundByID extends CrudException{
	public StaffNotFoundByID() {
		super("Cannot Found Staff By ID",HttpStatus.OK );
		// TODO Auto-generated constructor stub
	}
}
