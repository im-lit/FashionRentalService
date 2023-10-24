	
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
import com.example.fashionrentalservice.model.Response;
import com.example.fashionrentalservice.model.dto.product.RequestAddingProductDTO.AddProductStatus;
import com.example.fashionrentalservice.model.request.AddingProductRequestEntity;
import com.example.fashionrentalservice.model.response.AccountResponseEntity;
import com.example.fashionrentalservice.service.RequestAddingProductService;

import lombok.RequiredArgsConstructor;
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/request")
public class RequestAddingProductController {
	@Autowired
	private RequestAddingProductService requestAddService;
	
	//================================== Tạo mới Account ========================================	
	@PostMapping
	private ResponseEntity createRequest(@RequestBody AddingProductRequestEntity entity ) throws CrudException {		
		return ResponseEntity.ok().body(requestAddService.createRequest(entity));
	}
	
	//================================== Update ========================================	
	@PutMapping
	private ResponseEntity updateRequestStatusAndDes(@RequestParam int requestID,@RequestParam AddProductStatus status, @RequestParam String description) throws CrudException {		
		return ResponseEntity.ok().body(requestAddService.updateRequestStatusAndDes(requestID,status,description));
	}
	
	
	//================================== Lấy tất cả Account ========================================	
	@GetMapping("/getall")
	private ResponseEntity getAllRequest() {
		return ResponseEntity.ok().body(requestAddService.getAllRequest());
	}
	
	//================================== Lấy 1 account ========================================	
	@GetMapping("/{requestID}")
	private ResponseEntity getRequestByID(@PathVariable int requestID) throws CrudException{
		return ResponseEntity.ok().body(requestAddService.getRequestByID(requestID));
	}
	
	//================================== Xóa ========================================	
    @DeleteMapping()
    private ResponseEntity deleteExistedAccount(@RequestParam int requestID) {
        return ResponseEntity.ok().body(requestAddService.deleteExistedRequest(requestID));
    }
}