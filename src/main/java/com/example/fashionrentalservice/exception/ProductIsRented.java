package com.example.fashionrentalservice.exception;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;

import com.example.fashionrentalservice.exception.handlers.CrudException;

public class ProductIsRented extends CrudException{

	public ProductIsRented(String productName, LocalDate lastRentDate) {
		super("The Product " + productName  + " is Rented. The Product will avalaible after day: " + lastRentDate, HttpStatus.BAD_REQUEST);
		// TODO Auto-generated constructor stub
	}

}
