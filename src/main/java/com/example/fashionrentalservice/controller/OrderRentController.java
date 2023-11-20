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
	
	@GetMapping("/cancel/staff")
	private ResponseEntity getAllCanceledOrderRent() {
		return ResponseEntity.ok().body(rentService.getAllCanceledOrderRent());
	}
	
	@PostMapping
	private ResponseEntity createOrderRentAndOrderRentDetail(@RequestBody List<OrderRentRequestEntity> entity) throws CrudException {			
		return ResponseEntity.ok().body(rentService.createOrderRent(entity));
	}
	
	@PutMapping
	private ResponseEntity updateStatusOrder(@RequestParam int orderRentID, @RequestParam OrderRentStatus status) throws CrudException{
		return ResponseEntity.ok().body(rentService.updateOrderRentByOrderRentID(orderRentID, status));
	}
	@PutMapping("/updateorderrentcode")
	private ResponseEntity updateOrderCode(@RequestParam int orderRentID, @RequestParam String orderCode) throws CrudException{
		return ResponseEntity.ok().body(rentService.updateOrderCode(orderRentID, orderCode));
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
	
	
	
	@GetMapping("/po/three/{productownerID}")
	private ResponseEntity getAll3OrderRentbyProductOwnerID(@PathVariable int productownerID ) throws CrudException {
		return ResponseEntity.ok().body(rentService.getAll3StatusOrderRentByProductOwnerID(productownerID));
	}
	
	@GetMapping("/po/prepare/{productownerID}")
	private ResponseEntity getAllPrepareOrderRentbyProductOwnerID(@PathVariable int productownerID ) throws CrudException {
		return ResponseEntity.ok().body(rentService.getAllPrepareOrderRentByProductOwnerID(productownerID));
	}
	
	@GetMapping("/po/completed/{productownerID}")
	private ResponseEntity getAllCompletedOrderRentByProductOwnerID(@PathVariable int productownerID ) throws CrudException {
		return ResponseEntity.ok().body(rentService.getAllCompletedOrderRentByProductOwnerID(productownerID));
	}
	
	@GetMapping("/po/pending/{productownerID}")
	private ResponseEntity getAllPendingOrderRentByProductOwnerID(@PathVariable int productownerID ) throws CrudException {
		return ResponseEntity.ok().body(rentService.getAllPendingOrderRentByProductOwnerID(productownerID));
	}
	
	@GetMapping("/po/delivery/{productownerID}")
	private ResponseEntity getAllDeliveryOrderRentByProductOwnerID(@PathVariable int productownerID ) throws CrudException {
		return ResponseEntity.ok().body(rentService.getAllDeliveryOrderRentByProductOwnerID(productownerID));
	}
	
	@GetMapping("/po/renting/{productownerID}")
	private ResponseEntity getAllRentingOrderRentByProductOwnerID(@PathVariable int productownerID ) throws CrudException {
		return ResponseEntity.ok().body(rentService.getAllRentingOrderRentByProductOwnerID(productownerID));
	}
	
	@GetMapping("/po/returning/{productownerID}")
	private ResponseEntity getAllReturningOrderRentByProductOwnerID(@PathVariable int productownerID ) throws CrudException {
		return ResponseEntity.ok().body(rentService.getAllReturningOrderRentByProductOwnerID(productownerID));
	}
	
	@GetMapping("/po/rejecting/{productownerID}")
	private ResponseEntity getAllRejectingOrderRentByProductOwnerID(@PathVariable int productownerID ) throws CrudException {
		return ResponseEntity.ok().body(rentService.getAllRejectingOrderRentByProductOwnerID(productownerID));
	}
	
	@GetMapping("/po/rejectcompleted/{productownerID}")
	private ResponseEntity getAllRejectingCompletedOrderRentByProductOwnerID(@PathVariable int productownerID ) throws CrudException {
		return ResponseEntity.ok().body(rentService.getAllRejectingCompletedOrderRentByProductOwnerID(productownerID));
	}
	
	@GetMapping("/po/cancel/{productownerID}")
	private ResponseEntity getAllCancledOrderRentByProductOwnerID(@PathVariable int productownerID ) throws CrudException {
		return ResponseEntity.ok().body(rentService.getAllCanceledOrderRentByProductOwnerID(productownerID));
	}
	
	@GetMapping("/po/confirming/{productownerID}")
	private ResponseEntity getAllConfirmingOrderRentByProductOwnerID(@PathVariable int productownerID ) throws CrudException {
		return ResponseEntity.ok().body(rentService.getAllConfirmingOrderRentByProductOwnerID(productownerID));
	}
	
	//pending Pending, delivery Delivery,confirming Confirming renting Renting, returning Returning, rejecting Rejecting, rejectcompleted RejetingCompleted, cancel Cancled
	
	@GetMapping("/cus/prepare/{customerID}")
	private ResponseEntity getAllPrepareOrderRentbyCustomerID(@PathVariable int customerID ) throws CrudException {
		return ResponseEntity.ok().body(rentService.getAllPrepareOrderRentByCustomerID(customerID));
	}
	
	@GetMapping("/cus/confirming/{customerID}")
	private ResponseEntity getAllConfirmingOrderRentbyCustomerID(@PathVariable int customerID ) throws CrudException {
		return ResponseEntity.ok().body(rentService.getAllConfirmingOrderRentByCustomerID(customerID));
	}
	
	@GetMapping("/cus/completed/{customerID}")
	private ResponseEntity getAllCompletedOrderRentByCustomerID(@PathVariable int customerID ) throws CrudException {
		return ResponseEntity.ok().body(rentService.getAllCompletedOrderRentByCustomerID(customerID));
	}
	
	@GetMapping("/cus/pending/{customerID}")
	private ResponseEntity getAllPendingOrderRentByCustomerID(@PathVariable int customerID ) throws CrudException {
		return ResponseEntity.ok().body(rentService.getAllPendingOrderRentByCustomerID(customerID));
	}
	
	@GetMapping("/cus/delivery/{customerID}")
	private ResponseEntity getAllDeliveryOrderRentByCustomerID(@PathVariable int customerID ) throws CrudException {
		return ResponseEntity.ok().body(rentService.getAllDeliveryOrderRentByCustomerID(customerID));
	}
	
	@GetMapping("/cus/renting/{customerID}")
	private ResponseEntity getAllRentingOrderRentByCustomerID(@PathVariable int customerID ) throws CrudException {
		return ResponseEntity.ok().body(rentService.getAllRentingOrderRentByCustomerID(customerID));
	}
	
	@GetMapping("/cus/returning/{customerID}")
	private ResponseEntity getAllReturningOrderRentByCustomerID(@PathVariable int customerID ) throws CrudException {
		return ResponseEntity.ok().body(rentService.getAllReturningOrderRentByCustomerID(customerID));
	}
	
	@GetMapping("/cus/rejecting/{customerID}")
	private ResponseEntity getAllRejectingOrderRentByCustomerID(@PathVariable int customerID ) throws CrudException {
		return ResponseEntity.ok().body(rentService.getAllRejectingOrderRentByCustomerID(customerID));
	}
	
	@GetMapping("/cus/rejectcompleted/{customerID}")
	private ResponseEntity getAllRejectingCompletedOrderRentByCustomerID(@PathVariable int customerID ) throws CrudException {
		return ResponseEntity.ok().body(rentService.getAllRejectingCompletedOrderRentByCustomerID(customerID));
	}
	
	@GetMapping("/cus/cancel/{customerID}")
	private ResponseEntity getAllCancledOrderRentByCustomerID(@PathVariable int customerID ) throws CrudException {
		return ResponseEntity.ok().body(rentService.getAllCanceledOrderRentByCustomerID(customerID));
	}
	
}
