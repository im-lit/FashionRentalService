package com.example.fashionrentalservice.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.dto.account.WalletDTO;
import com.example.fashionrentalservice.model.request.TransactionHistoryRequestEntity;
import com.example.fashionrentalservice.service.AccountService;
import com.example.fashionrentalservice.service.TransactionHistoryService;
import com.example.fashionrentalservice.service.VNPayService;
import com.example.fashionrentalservice.service.WalletService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/VNPaycontroller")
public class VNPayController {
	@Autowired
	private VNPayService vnPayService;
	@Autowired
	private AccountService accService;

	@Autowired
	private WalletService walletService;
	
	@Autowired
	private TransactionHistoryService transService;

	private int accountIDLocal;
	
	private double amount;

	@PostMapping("/submitOrder")
	public String submitOrder(@RequestParam("amount") int orderTotal, @RequestParam("accountID") int accountID,
			@RequestParam("orderInfo") String orderInfo, HttpServletRequest request) {
		String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		String vnpayUrl = vnPayService.createOrder(orderTotal, orderInfo, baseUrl);
		accountIDLocal = accountID;
		amount = orderTotal;
		return vnpayUrl;
	}

	@GetMapping("/vnpay-payment")
    public String GetMapping(HttpServletRequest request, Model model) throws CrudException{

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");
        
        int paymentStatus =vnPayService.orderReturn(request);
        if(paymentStatus == 1) {
        	WalletDTO dto = accService.getWalletByAccountID(accountIDLocal);
        	walletService.updateBalance(dto.getWalletID(),amount);
        	
        	TransactionHistoryRequestEntity entity = new TransactionHistoryRequestEntity();
        	entity.setAccountID(accountIDLocal);
        	entity.setAmount(amount);
        	entity.setDescription("nap " + amount + " tu ngan hang NCB");
        	entity.setTransactionType("nap tien");       	
        	transService.createTransactionHistory(entity);      	
        }

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        return paymentStatus == 1 ? "ordersuccess" : "orderfail";
    }
}