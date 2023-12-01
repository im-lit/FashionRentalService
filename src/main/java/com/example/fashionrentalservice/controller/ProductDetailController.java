package com.example.fashionrentalservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.request.ProductDetailRequestEntity;
import com.example.fashionrentalservice.service.ProductDetailService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/productdetail")
public class ProductDetailController {
	@Autowired
	private ProductDetailService pdetailService;
	
	
	
	@PostMapping
	private ResponseEntity createProductDetail(@RequestBody List<ProductDetailRequestEntity> entity) throws CrudException {
		return ResponseEntity.ok().body(pdetailService.createProductDetail(entity));
	}
	
	
    @DeleteMapping()
    private ResponseEntity deleteExistedRentalPrices(@RequestParam int id) {
        return ResponseEntity.ok().body(pdetailService.deleteExistedProductDetail(id));
    }
}
