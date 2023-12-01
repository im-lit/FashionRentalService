package com.example.fashionrentalservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.dto.product.FeedBackDTO;
import com.example.fashionrentalservice.model.request.FavoriteProductRequestEntity;
import com.example.fashionrentalservice.model.request.FeedBackRequestEntity;
import com.example.fashionrentalservice.service.FavoriteProductService;
import com.example.fashionrentalservice.service.FeedBackService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/feedback")
public class FeedbackController {
	@Autowired
	private FeedBackService fbService;
	
	@GetMapping("/{productID}")
	private ResponseEntity getFeedBackByProductID(@PathVariable int productID) throws CrudException {
		return ResponseEntity.ok().body(fbService.getFeedBackByProductID(productID));
	}
	
	@PostMapping
	private ResponseEntity createFeedBackProduct(@RequestBody FeedBackRequestEntity entity) throws CrudException {
		return ResponseEntity.ok().body(fbService.createFeedBackProduct(entity));
	}
}
