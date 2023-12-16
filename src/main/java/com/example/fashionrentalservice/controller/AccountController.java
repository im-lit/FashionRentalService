	
package com.example.fashionrentalservice.controller;


import java.util.List;

import com.example.fashionrentalservice.model.request.LoginGoogleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.Response;
import com.example.fashionrentalservice.model.dto.account.AccountDTO.AccountStatus;
import com.example.fashionrentalservice.model.request.AccountRequestEntity;
import com.example.fashionrentalservice.model.request.ProductRequestEntity;
import com.example.fashionrentalservice.model.response.AccountResponseEntity;
import com.example.fashionrentalservice.service.AccountService;

import lombok.RequiredArgsConstructor;
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
	@Autowired
	private AccountService accService;
	//================================== Login ========================================	
	@PostMapping("/login")
	private ResponseEntity checkLogin(@RequestParam String email, @RequestParam String password) throws CrudException {	
		return ResponseEntity.ok().body(accService.login(email, password));
	}

	@PostMapping("/login-gg")
	private ResponseEntity checkLoginGoogle(@RequestBody LoginGoogleRequest loginGoogleRequest) throws CrudException {
		return ResponseEntity.ok().body(accService.loginGoogle(loginGoogleRequest.getToken()));
	}
	
	//================================== Tạo mới Account ========================================	
	@PostMapping("/create")
	private ResponseEntity createNewAccount(@RequestBody AccountRequestEntity entity ) throws CrudException {		
		return ResponseEntity.ok().body(accService.createNewAccount(entity));
	}
	
	//================================== Update ========================================	
	@PutMapping("/update")
	private ResponseEntity updatePasswordAccount(@RequestParam int accountID,@RequestParam String password) throws CrudException {		
		return ResponseEntity.ok().body(accService.updatePasswordAccount(accountID,password));
	}
	
	@PutMapping("/updatestatus")
	private ResponseEntity updateStatusAccount(@RequestParam int accountID,@RequestParam AccountStatus status) throws CrudException {		
		return ResponseEntity.ok().body(accService.updateStatusAccount(accountID,status));
	}
	
	//================================== Lấy tất cả Account ========================================	
	@GetMapping("/getall")
	private ResponseEntity getAllAccount() {
		return ResponseEntity.ok().body(accService.getAllAccount());
	}
	
	//================================== Lấy 1 account ========================================	
	@GetMapping("/{accountID}")
	private ResponseEntity getAccountByID(@PathVariable int accountID) throws CrudException{
		 Response<AccountResponseEntity> response  = new Response<AccountResponseEntity>(200, "Success", accService.getAccountByID(accountID));
		return ResponseEntity.ok().body(response);
	}
	
	//================================== Xóa ========================================	
    @DeleteMapping()
    private ResponseEntity deleteExistedAccount(@RequestParam int id) throws CrudException {
        return ResponseEntity.ok().body(accService.deleteExistedAccount(id));
    }
}