package com.example.fashionrentalservice.exception;

import org.springframework.http.HttpStatus;

import com.example.fashionrentalservice.exception.handlers.CrudException;

public class ProductStatusNotApproved  extends CrudException{
		public ProductStatusNotApproved() {
			// TODO Auto-generated constructor stub
		 
			super("This Product is Not Approved",HttpStatus.BAD_REQUEST );
			// TODO Auto-generated constructor stub
		}
}

