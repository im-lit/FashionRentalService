package com.example.fashionrentalservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.request.OrderBuyRequestEntity;
import com.example.fashionrentalservice.service.OrderBuyDetailService;
import com.example.fashionrentalservice.service.OrderBuyService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/orderbuy")
public class OrderBuyController {
	@Autowired
	private OrderBuyService buyService;
	
	@Autowired
	private OrderBuyDetailService buyDetailService;
	
//================================== Lay tat ca Customer ========================================
	@GetMapping("/staff")
	private ResponseEntity getAllOrderBuy() {
		return ResponseEntity.ok().body(buyService.getAllOrder());
	}
	
	@PostMapping
	private ResponseEntity createOrderBuyAndOrderBuyDetail(@RequestBody List<OrderBuyRequestEntity> entity) throws CrudException {			
		return ResponseEntity.ok().body( buyService.createOrderBuy(entity));
	}
	
	
	@GetMapping("/customer/{customerID}")
	private ResponseEntity getAllOrderBuyByCustomerID(@PathVariable int customerID) throws CrudException{
		return ResponseEntity.ok().body(buyService.getAllOrderByCustomerID(customerID));
	}
	
	@GetMapping("/po/{productownerID}")
	private ResponseEntity getAllOrderBuyByProductOwnerID(@PathVariable int productownerID) throws CrudException{
		return ResponseEntity.ok().body(buyService.getAllOrderByProductOwnerID(productownerID));
	}
	
//	@GetMapping("/revenue/{productownerID}")
//	private ResponseEntity getRevenue1Month(@PathVariable int productownerID) throws CrudException{
//		return ResponseEntity.ok().body(buyService.getTotalRevenue1MonthByProductOwnerID(productownerID));
//	}
	
}
