package com.example.fashionrentalservice.exception;

import org.springframework.http.HttpStatus;

import com.example.fashionrentalservice.exception.handlers.CrudException;

public class DaysBetweenTwoDays extends CrudException {
	 public DaysBetweenTwoDays() {
		// TODO Auto-generated constructor stub
		super("EndDate Must Greater StartDate",HttpStatus.BAD_REQUEST );
		// TODO Auto-generated constructor stub
	}
		
	
}
