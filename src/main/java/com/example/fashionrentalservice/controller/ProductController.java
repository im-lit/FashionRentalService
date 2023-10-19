package com.example.fashionrentalservice.controller;

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
import com.example.fashionrentalservice.model.request.ProductRequestEntity;
import com.example.fashionrentalservice.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private ProductService productService;
	
	
 //================================== Đăng kí========================================
	@PostMapping("/create")
	private ResponseEntity createProduct(@RequestBody ProductRequestEntity entity) throws CrudException{
		return ResponseEntity.ok().body(productService.createProduct(entity));
	}
	
	@GetMapping("/{productID}")
	private ResponseEntity getProductByID(@PathVariable int productID) throws CrudException {
		return ResponseEntity.ok().body(productService.getProductbyID(productID));
	}
	
	
////================================== Xóa ProductOwner ========================================	
//    @DeleteMapping()
//    private ResponseEntity deleteExistedCustomer(@RequestParam int id) {
//        return ResponseEntity.ok().body(productService.deleteExistedCustomer(id));
//    }
}
