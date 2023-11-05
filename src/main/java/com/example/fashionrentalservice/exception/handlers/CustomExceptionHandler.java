package com.example.fashionrentalservice.exception.handlers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.fashionrentalservice.exception.AccNotFoundByID;
import com.example.fashionrentalservice.exception.AccountIsRegisted;
import com.example.fashionrentalservice.exception.BalanceNegative;
import com.example.fashionrentalservice.exception.CreateCustomerFail;
import com.example.fashionrentalservice.exception.CreateOrderBuyDetailFailed;
import com.example.fashionrentalservice.exception.CreateOrderFailed;
import com.example.fashionrentalservice.exception.CusNotFoundByID;
import com.example.fashionrentalservice.exception.DaysBetweenTwoDays;
import com.example.fashionrentalservice.exception.EmailExisted;
import com.example.fashionrentalservice.exception.LoginFail;
import com.example.fashionrentalservice.exception.OrderBuyIDNotFound;
import com.example.fashionrentalservice.exception.PONotFoundByID;
import com.example.fashionrentalservice.exception.ProductNotAvailable;
import com.example.fashionrentalservice.exception.ProductNotFoundByID;
import com.example.fashionrentalservice.exception.StaffNotFoundByID;
import com.example.fashionrentalservice.exception.TransactionHistoryCreatedFailed;
import com.example.fashionrentalservice.exception.UpdatePasswordFail;
import com.example.fashionrentalservice.exception.WalletInOrderServiceFailed;



@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler{
	@ExceptionHandler(value = {
		LoginFail.class,
		EmailExisted.class,
		UpdatePasswordFail.class,
		CreateCustomerFail.class,
		PONotFoundByID.class,
		CusNotFoundByID.class,
		AccNotFoundByID.class,
		StaffNotFoundByID.class,
		BalanceNegative.class,
		CreateOrderFailed.class,
		ProductNotFoundByID.class,
		OrderBuyIDNotFound.class,
		CreateOrderBuyDetailFailed.class,
		WalletInOrderServiceFailed.class,
		TransactionHistoryCreatedFailed.class,
		ProductNotAvailable.class,
		AccountIsRegisted.class,
		DaysBetweenTwoDays.class
		
	})
	protected ResponseEntity<Response> handleAuthExceptions(CrudException exception) {
		return ResponseEntity
				.status(exception.getStatus())
				.body(new Response("Error", exception.getMessage()));
	}
	
}
