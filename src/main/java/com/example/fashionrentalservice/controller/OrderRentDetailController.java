package com.example.fashionrentalservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.service.OrderRentDetailService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/orderrentdetail")
public class OrderRentDetailController {
	@Autowired
	private OrderRentDetailService rentDetailService;
	
	
//	@PostMapping
//	private ResponseEntity createOrderBuyAndOrderBuyDetail(@RequestBody List<OrderBuyDetailRequestEntity> entity) throws CrudException {			
//		return ResponseEntity.ok().body(buyDetailService.createOrderBuyDetail(entity));
//	}
	
//================================== Lay tat ca Customer ========================================
	@GetMapping("/{orderrentID}")
	private ResponseEntity getAllOrderRentDetailByOrderRentID(@PathVariable int orderrentID ) {
		return ResponseEntity.ok().body(rentDetailService.getAllOrderDetailByOrderRentID(orderrentID));
	}
	
	@GetMapping("/{productID}/date")
	private ResponseEntity getOrderRentDetailByProductIDAndOrderRentStatusRenting(@PathVariable int productID ) throws CrudException {
		return ResponseEntity.ok().body(rentDetailService.getOrderRentDetailByProductIDAndOrderRentStatusRenting(productID));
	}
	@GetMapping("/{productID}/rentdetail")
	private ResponseEntity getOrderRentDetailByProductID(@PathVariable int productID ) throws CrudException {
		return ResponseEntity.ok().body(rentDetailService.getOrderRentDetailByProductID(productID));
	}
	
	@GetMapping("/{productID}/{orderRentID}/rentdetail")
	private ResponseEntity getOrderRentDetailByProductIDAndOrderRentID(@PathVariable int productID, @PathVariable int orderRentID) throws CrudException {
		return ResponseEntity.ok().body(rentDetailService.getOrderRentDetailByProductIDAndOrderRentID(productID, orderRentID));
	}
	
}
