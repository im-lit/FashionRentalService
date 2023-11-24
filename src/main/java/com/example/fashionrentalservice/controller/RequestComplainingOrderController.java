	
package com.example.fashionrentalservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.dto.product.RequestComplainingOrderDTO.ComplainingOrderStatus;
import com.example.fashionrentalservice.model.request.RequestComplainingOrderRequestEntity;
import com.example.fashionrentalservice.service.RequestComplainingOrderService;

import lombok.RequiredArgsConstructor;
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/complaining")
public class RequestComplainingOrderController {
	@Autowired
	private RequestComplainingOrderService requestComService;
	
	//================================== Tạo mới Request ========================================	
	@PostMapping
	private ResponseEntity createRequest(@RequestBody RequestComplainingOrderRequestEntity entity ) throws CrudException {		
		return ResponseEntity.ok().body(requestComService.createRequestComplaining(entity));
	}
	
	//================================== Update ========================================	
	@PutMapping
	private ResponseEntity updateRequestStatus(@RequestParam int requestID,@RequestParam ComplainingOrderStatus status, @RequestParam int staffID, @RequestParam String staffResponse) throws CrudException {		
		return ResponseEntity.ok().body(requestComService.updateRequestStatus(requestID, status, staffID, staffResponse));
	}
	
	
	//================================== Lấy tất cả Account ========================================	
	@GetMapping("/getapproving")
	private ResponseEntity getApprovingRequest() {
		return ResponseEntity.ok().body(requestComService.getApprovingRequest());
	}
	
	//================================== Lấy 1 account ========================================	
	@GetMapping("/{requestID}")
	private ResponseEntity getRequestByID(@PathVariable int requestID) throws CrudException{
		return ResponseEntity.ok().body(requestComService.getRequestByID(requestID));
	}
	
	//================================== Xóa ========================================	
    @DeleteMapping()
    private ResponseEntity deleteExistedRequest(@RequestParam int requestID) throws CrudException {
        return ResponseEntity.ok().body(requestComService.deleteExistedRequestComplaining(requestID));
    }
}