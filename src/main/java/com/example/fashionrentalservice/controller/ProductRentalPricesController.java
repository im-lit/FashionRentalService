package com.example.fashionrentalservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.fashionrentalservice.model.request.ProductRentalPricesRequestEntity;
import com.example.fashionrentalservice.service.ProductRentalPricesService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/rentprice")
public class ProductRentalPricesController {
	@Autowired
	private ProductRentalPricesService rentalPriceService;
	
	@GetMapping("/{productID}")
	private ResponseEntity getProductRentalPricesByProductID(@PathVariable int productID) {
		return ResponseEntity.ok().body(rentalPriceService.getAllRentPricesByProductID(productID));
	}
	
	
	@PostMapping
	private ResponseEntity createProductRentalPrices(@RequestBody ProductRentalPricesRequestEntity entity) {
		return ResponseEntity.ok().body(rentalPriceService.createRentalPrices(entity));
	}
	
	
    @DeleteMapping()
    private ResponseEntity deleteExistedRentalPrices(@RequestParam int id) {
        return ResponseEntity.ok().body(rentalPriceService.deleteRentalPrices(id));
    }
}
