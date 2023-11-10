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
import com.example.fashionrentalservice.model.dto.order.OrderBuyDTO.OrderBuyStatus;
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
	
	@PutMapping
	private ResponseEntity updateStatusOrder(@RequestParam int orderBuyID, @RequestParam OrderBuyStatus status) throws CrudException{
		return ResponseEntity.ok().body(buyService.updateOrderBuyByOrderBuyID(orderBuyID, status));
	}
	
	@PutMapping("/updateorderbuycode")
	private ResponseEntity updateOrderCode(@RequestParam int orderBuyID, @RequestParam String orderCode) throws CrudException{
		return ResponseEntity.ok().body(buyService.updateOrderCode(orderBuyID, orderCode));
	}
	
	
	
	@GetMapping("/customer/pending/{customerID}")
	private ResponseEntity getAllPendingOrderBuyByCustomerID(@PathVariable int customerID) throws CrudException{
		return ResponseEntity.ok().body(buyService.getAllPendingOrderByCustomerID(customerID));
	}
	@GetMapping("/customer/rejecting/{customerID}")
	private ResponseEntity getAllRejectingOrderBuyByCustomerID(@PathVariable int customerID) throws CrudException{
		return ResponseEntity.ok().body(buyService.getAllRejectingOrderByCustomerID(customerID));
	}
	@GetMapping("/customer/rejectingcompleted/{customerID}")
	private ResponseEntity getAllRejectingCompletedOrderBuyByCustomerID(@PathVariable int customerID) throws CrudException{
		return ResponseEntity.ok().body(buyService.getAllRejectingCompletedOrderByCustomerID(customerID));
	}
	@GetMapping("/customer/readypick/{customerID}")
	private ResponseEntity getAllReadyPickUpOrderBuyByCustomerID(@PathVariable int customerID) throws CrudException{
		return ResponseEntity.ok().body(buyService.getAllReadyPickUpOrderCustomerID(customerID));
	}
	@GetMapping("/customer/canceled/{customerID}")
	private ResponseEntity getAllCanceledOrderBuyByCustomerID(@PathVariable int customerID) throws CrudException{
		return ResponseEntity.ok().body(buyService.getAllCanceledOrderByCustomerID(customerID));
	}
	@GetMapping("/customer/completed/{customerID}")
	private ResponseEntity getAllCompletedOrderBuyByCustomerID(@PathVariable int customerID) throws CrudException{
		return ResponseEntity.ok().body(buyService.getAllRejectingCompletedOrderByCustomerID(customerID));
	}
	@GetMapping("/customer/prepare/{customerID}")
	private ResponseEntity getAllPrepareOrderBuyByCustomerID(@PathVariable int customerID) throws CrudException{
		return ResponseEntity.ok().body(buyService.getAllPrepareOrderByCustomerID(customerID));
	}
	
	
	
	
	
	@GetMapping("/po/pending/{productownerID}")
	private ResponseEntity getAllPendingOrderBuyByProductOwnerID(@PathVariable int productownerID) throws CrudException{
		return ResponseEntity.ok().body(buyService.getAllPendingOrderByProductOwnerID(productownerID));
	}
	
	@GetMapping("/po/rejecting/{productownerID}")
	private ResponseEntity getAllRejectingOrderBuyByProductOwnerID(@PathVariable int productownerID) throws CrudException{
		return ResponseEntity.ok().body(buyService.getAllRejectingOrderByProductOwnerID(productownerID));
	}
	@GetMapping("/po/rejectingcompleted/{productownerID}")
	private ResponseEntity getAllRejectingCompletedOrderBuyByProductOwnerID(@PathVariable int productownerID) throws CrudException{
		return ResponseEntity.ok().body(buyService.getAllRejectingCompletedOrderByProductOwnerID(productownerID));
	}
	@GetMapping("/po/readypick/{productownerID}")
	private ResponseEntity getAllReadyPickUpOrderBuyByProductOwnerID(@PathVariable int productownerID) throws CrudException{
		return ResponseEntity.ok().body(buyService.getAllReadyPickUpOrderByProductOwnerID(productownerID));
	}
	@GetMapping("/po/canceled/{productownerID}")
	private ResponseEntity getAllCanceledOrderBuyByProductOwnerID(@PathVariable int productownerID) throws CrudException{
		return ResponseEntity.ok().body(buyService.getAllCanceledOrderByProductOwnerID(productownerID));
	}
	@GetMapping("/po/completed/{productownerID}")
	private ResponseEntity getAllCompletedOrderBuyByProductOwnerID(@PathVariable int productownerID) throws CrudException{
		return ResponseEntity.ok().body(buyService.getAllRejectingCompletedOrderByProductOwnerID(productownerID));
	}
	@GetMapping("/po/prepare/{productownerID}")
	private ResponseEntity getAllPrepareOrderBuyByProductOwnerID(@PathVariable int productownerID) throws CrudException{
		return ResponseEntity.ok().body(buyService.getAllPrepareOrderByProductOwnerID(productownerID));
	}
	
	
	@GetMapping("/1month/{productownerID}")
	private ResponseEntity getOrderBuy1Month(@PathVariable int productownerID) throws CrudException{
		return ResponseEntity.ok().body(buyService.getTotal1MonthOrderByProductOwnerID(productownerID));
	}
	
	@GetMapping("/1week/{productownerID}")
	private ResponseEntity getOrderBuy1week(@PathVariable int productownerID) throws CrudException{
		return ResponseEntity.ok().body(buyService.getTotal1WeekOrderByProductOwnerID(productownerID));
	}
	
}
