package com.example.fashionrentalservice.exception;

import org.springframework.http.HttpStatus;

import com.example.fashionrentalservice.exception.handlers.CrudException;




	public class ProductStatusOnSale extends CrudException{
		public ProductStatusOnSale() {
			super("This Product is on Renting",HttpStatus.BAD_REQUEST );
			// TODO Auto-generated constructor stub
		}
}
