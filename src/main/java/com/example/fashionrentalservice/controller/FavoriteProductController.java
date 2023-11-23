package com.example.fashionrentalservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.request.FavoriteProductRequestEntity;
import com.example.fashionrentalservice.model.request.VoucherRequestEntity;
import com.example.fashionrentalservice.model.response.FavoriteProductResponseEntity;
import com.example.fashionrentalservice.repositories.FavoriteProductRepository;
import com.example.fashionrentalservice.service.FavoriteProductService;
import com.example.fashionrentalservice.service.VoucherService;

public class FavoriteProductController {
	@Autowired
	private FavoriteProductService fpService;
	
	@PostMapping
	private ResponseEntity createFavoriteProduct(@RequestBody FavoriteProductRequestEntity entity) throws CrudException {
		return ResponseEntity.ok().body(fpService.createFavoriteProduct(entity));
	}
}
