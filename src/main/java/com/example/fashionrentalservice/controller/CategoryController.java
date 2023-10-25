
package com.example.fashionrentalservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.service.CategoryService;

import lombok.RequiredArgsConstructor;
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	private CategoryService cateService;
	
	//================================== Tạo mới Category ========================================	
	@PostMapping
	private ResponseEntity createNewCategory(@RequestParam String name ) throws CrudException {		
		return ResponseEntity.ok().body(cateService.createCategory(name));
	}
	
	//================================== Lấy tất cả Category ========================================	
	@GetMapping("/getall")
	private ResponseEntity getAllCategory() {
		return ResponseEntity.ok().body(cateService.getAllCategory());
	}
	
	

}