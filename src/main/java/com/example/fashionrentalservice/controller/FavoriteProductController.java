package com.example.fashionrentalservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
import com.example.fashionrentalservice.model.request.FavoriteProductRequestEntity;
import com.example.fashionrentalservice.model.request.VoucherRequestEntity;
import com.example.fashionrentalservice.model.response.FavoriteProductResponseEntity;
import com.example.fashionrentalservice.repositories.FavoriteProductRepository;
import com.example.fashionrentalservice.service.FavoriteProductService;
import com.example.fashionrentalservice.service.VoucherService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/favoriteproduct")
public class FavoriteProductController {
	@Autowired
	private FavoriteProductService fpService;
	
	@PostMapping
	private ResponseEntity createFavoriteProduct(@RequestBody FavoriteProductRequestEntity entity) throws CrudException {
		return ResponseEntity.ok().body(fpService.createFavoriteProduct(entity));
	}
	
	@GetMapping("/{customerID}")
	private ResponseEntity getFavoriteByCusID(@PathVariable int customerID) throws CrudException {
		return ResponseEntity.ok().body(fpService.getFavoriteProductByCusID(customerID));
	}
	@PutMapping
	private ResponseEntity unmarkFavoriteStatus(@RequestParam int favoriteproductID) throws CrudException {
		return ResponseEntity.ok().body(fpService.unmarkFavoriteByFavoriteID(favoriteproductID));
	}
}
