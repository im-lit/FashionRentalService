package com.example.fashionrentalservice.exception.handlers;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrudException extends Exception{
	private final HttpStatus status;
    private final String message;

    public CrudException(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }
}