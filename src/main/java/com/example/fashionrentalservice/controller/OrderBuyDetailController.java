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
import com.example.fashionrentalservice.model.request.OrderBuyDetailRequestEntity;
import com.example.fashionrentalservice.service.OrderBuyDetailService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/orderbuydetail")
public class OrderBuyDetailController {
	@Autowired
	private OrderBuyDetailService buyDetailService;
	
	
	@PostMapping
	private ResponseEntity createOrderBuyAndOrderBuyDetail(@RequestBody List<OrderBuyDetailRequestEntity> entity) throws CrudException {			
		return ResponseEntity.ok().body(buyDetailService.createOrderBuyDetail(entity));
	}
	
//================================== Lay tat ca Customer ========================================
	@GetMapping("/{orderID}")
	private ResponseEntity getAllOrderBuyDetailByOrderBuyID(@PathVariable int orderID ) {
		return ResponseEntity.ok().body(buyDetailService.getAllOrderDetailByOrderBuyID(orderID));
	}
	
}
