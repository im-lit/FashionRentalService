package com.example.fashionrentalservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.dto.order.OrderRentDTO.OrderRentStatus;
import com.example.fashionrentalservice.model.request.OrderRentRequestEntity;
import com.example.fashionrentalservice.service.OrderRentDetailService;
import com.example.fashionrentalservice.service.OrderRentService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/orderrent")
public class OrderRentController {
	@Autowired
	private OrderRentService rentService;
	
	@Autowired
	private OrderRentDetailService rentDetailService;
	
//================================== Lay tat ca Customer ========================================
	@GetMapping("/staff")
	private ResponseEntity getAllOrderRent() {
		return ResponseEntity.ok().body(rentService.getAllOrder());
	}
	
	@PostMapping
	private ResponseEntity createOrderRentAndOrderRentDetail(@RequestBody List<OrderRentRequestEntity> entity) throws CrudException {			
		return ResponseEntity.ok().body(rentService.createOrderRent(entity));
	}
	
	@PutMapping
	private ResponseEntity updateStatusOrder(@RequestParam int orderRentID, @RequestParam OrderRentStatus status) throws CrudException{
		return ResponseEntity.ok().body(rentService.updateOrderRentByOrderRentID(orderRentID, status));
	}
	
	
	@GetMapping("/customer/{customerID}")
	private ResponseEntity getAllOrderRentBuyByCustomerID(@PathVariable int customerID) throws CrudException{
		return ResponseEntity.ok().body(rentService.getAllOrderRentByCustomerID(customerID));
	}
	
	@GetMapping("/po/{productownerID}")
	private ResponseEntity getAllOrderRentByProductOwnerID(@PathVariable int productownerID) throws CrudException{
		return ResponseEntity.ok().body(rentService.getAllOrderRentByProductOwnerID(productownerID));
	}
	
	@GetMapping("/1month/{productownerID}")
	private ResponseEntity getOrderRent1Month(@PathVariable int productownerID) throws CrudException{
		return ResponseEntity.ok().body(rentService.getTotal1MonthOrderByProductOwnerID(productownerID));
	}
	
	@GetMapping("/1week/{productownerID}")
	private ResponseEntity getOrderRent1week(@PathVariable int productownerID) throws CrudException{
		return ResponseEntity.ok().body(rentService.getTotalOrderRent1WeekOrderByProductOwnerID(productownerID));
	}
	
}