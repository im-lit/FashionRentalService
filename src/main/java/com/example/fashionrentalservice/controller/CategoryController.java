//
//package com.example.fashionrentalservice.controller;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//
//import com.example.fashionrentalservice.exception.handlers.CrudException;
//import com.example.fashionrentalservice.model.dto.account.AccountDTO;
//import com.example.fashionrentalservice.model.response.AccountResponseEntity;
//
//import com.example.fashionrentalservice.model.request.AccountRequestEntity;
//
//import com.example.fashionrentalservice.service.AccountService;
//
//import lombok.RequiredArgsConstructor;
//@RestController
//@CrossOrigin
//@RequiredArgsConstructor
//@RequestMapping("/category")
//public class CategoryController {
//	@Autowired
//	private AccountService accService;
//	//================================== Login ========================================	
//	@PostMapping("/login")
//	private ResponseEntity checkLogin(@RequestParam String email, @RequestParam String password) throws CrudException {	
//		return ResponseEntity.ok().body(accService.login(email, password));
//	}
//	
//	//================================== Tạo mới Account ========================================	
//	@PostMapping("/create")
//	private ResponseEntity createNewAccount(@RequestBody AccountRequestEntity entity ) throws CrudException {		
//		return ResponseEntity.ok().body(accService.createNewAccount(entity));
//	}
//	
//	//================================== Update ========================================	
//	@PutMapping("/update")
//	private ResponseEntity updateCategory(@RequestParam int accountID,@RequestParam String password) throws CrudException {		
//		return ResponseEntity.ok().body(accService.updatePasswordAccount(accountID,password));
//	}
//	
//	//================================== Lấy tất cả Account ========================================	
//	@GetMapping("/getall")
//	private ResponseEntity getAllAccount() {
//		return ResponseEntity.ok().body(accService.getAllAccount());
//	}
//	
//	//================================== Lấy 1 account ========================================	
//	@GetMapping("/{accountID}")
//	private ResponseEntity getAccountByID(@PathVariable int accountID) throws CrudException{
//		return ResponseEntity.ok().body(accService.getAccountByID(accountID));
//	}
//	
//	//================================== Xóa ========================================	
//    @DeleteMapping()
//    private ResponseEntity deleteExistedAccount(@RequestParam int id) {
//        return ResponseEntity.ok().body(accService.deleteExistedAccount(id));
//    }
//}