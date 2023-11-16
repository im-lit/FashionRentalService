	
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

import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.request.AddressRequestEntity;
import com.example.fashionrentalservice.model.request.RentalProductImgRequestEntity;
import com.example.fashionrentalservice.service.AdressService;
import com.example.fashionrentalservice.service.RentalProductImgService;

import lombok.RequiredArgsConstructor;
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/pic")
public class RentalProductImgController {
	@Autowired
	private RentalProductImgService rentPicService;

	
	
	//================================== Tạo mới Account ========================================	
	@PostMapping
	private ResponseEntity createNewPic(@RequestBody RentalProductImgRequestEntity entity ) throws CrudException {		
		return ResponseEntity.ok().body(rentPicService.createPicture(entity));
	}
	
	
	
	//================================== Lấy tất cả Address ========================================	
	@GetMapping("/{accountID}")
	private ResponseEntity getAllRentalProductImgByAccountID(@PathVariable int accountID) throws CrudException {
		return ResponseEntity.ok().body(rentPicService.getAllRentalProductImgByAccountID(accountID));
	}
	
	@GetMapping("/{orderdetailID}")
	private ResponseEntity getAllRentalProductImgByOrderDetailID(@PathVariable int orderdetailID) throws CrudException {
		return ResponseEntity.ok().body(rentPicService.getAllRentalProductImgByOrderDetailID(orderdetailID));
	}
	
	
	//================================== Xóa ========================================	
    @DeleteMapping
    private ResponseEntity deletePic(@RequestParam int picID) throws CrudException {
        return ResponseEntity.ok().body(rentPicService.deletePicByID(picID));
    }
}