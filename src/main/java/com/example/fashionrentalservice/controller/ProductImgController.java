package com.example.fashionrentalservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.request.ProductImgRequestEntity;
import com.example.fashionrentalservice.service.ProductlmgService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/productimg")
public class ProductImgController {
	@Autowired
	private ProductlmgService imgService;
	
//================================== Lay tat ca Customer ========================================
	@GetMapping
	private ResponseEntity getAllProductImgbyProductID(@RequestParam int productID) throws CrudException {
		return ResponseEntity.ok().body(imgService.getAllProductImgbyProductID(productID));
	}
	
	@PostMapping
	private ResponseEntity createProductImg(@RequestBody ProductImgRequestEntity entity)throws CrudException {
		return ResponseEntity.ok().body(imgService.CreateProductImg(entity));
	}
	
    @DeleteMapping()
    private ResponseEntity deleteProductImg(@RequestParam int id) throws CrudException{
        return ResponseEntity.ok().body(imgService.deleteProductImg(id));
    }
}
