package com.example.fashionrentalservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.fashionrentalservice.model.request.VoucherRequestEntity;
import com.example.fashionrentalservice.model.request.WalletRequestEntity;
import com.example.fashionrentalservice.service.VoucherService;


import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/voucher")
public class VoucherController {
	@Autowired
	private VoucherService voucherService;
//	
//	@PostMapping
//	private ResponseEntity createWallet(@RequestBody WalletRequestEntity entity) throws CrudException {
//		return ResponseEntity.ok().body(walletService.createWallet(entity));
//	}
	@PostMapping
	private ResponseEntity createVoucher(@RequestBody VoucherRequestEntity entity) throws CrudException {
		return ResponseEntity.ok().body(voucherService.createVoucher(entity));
	}
	@PostMapping("/{voucherCode}")
	private ResponseEntity useVoucher(@PathVariable String voucherCode) throws CrudException {
		return ResponseEntity.ok().body(voucherService.useVoucher(voucherCode));
	}
	
	@GetMapping("/{productownerID}")
	private ResponseEntity getVoucherByProrductOwnerIDNotExpired(@PathVariable int productownerID) throws CrudException {
		return ResponseEntity.ok().body(voucherService.getVoucherByProductOwnerIDNotExpired(productownerID));
	}
	@GetMapping("/getall/{productownerID}")
	private ResponseEntity getVoucherByProrductOwnerID(@PathVariable int productownerID) throws CrudException {
		return ResponseEntity.ok().body(voucherService.getVoucherByProductOwnerID(productownerID));
	}
	@GetMapping("/getall")
	private ResponseEntity getAllVoucher() throws CrudException {
		return ResponseEntity.ok().body(voucherService.getAllVoucher());
	}
	@PutMapping
	private ResponseEntity updateVoucherStatus(@RequestParam int voucherID) throws CrudException {
		return ResponseEntity.ok().body(voucherService.updateStatusVoucherByVoucherID(voucherID));
	}
}
