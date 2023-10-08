package com.example.fashionrentalservice.exception.handlers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.fashionrentalservice.exception.EmailExisted;
import com.example.fashionrentalservice.exception.LoginFail;
import com.example.fashionrentalservice.exception.UpdateFail;



@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler{
	@ExceptionHandler(value = {
		LoginFail.class,
		EmailExisted.class,
		UpdateFail.class
	})
	protected ResponseEntity<Response> handleAuthExceptions(CrudException exception) {
		return ResponseEntity
				.status(exception.getStatus())
				.body(new Response("Error", exception.getMessage()));
	}
	
}
