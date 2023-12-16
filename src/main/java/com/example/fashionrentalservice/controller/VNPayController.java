package com.example.fashionrentalservice.controller;

import java.sql.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.dto.account.WalletDTO;
import com.example.fashionrentalservice.model.request.TransactionHistoryRequestEntity;
import com.example.fashionrentalservice.service.AccountService;
import com.example.fashionrentalservice.service.NotificationService;
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
	
	@Autowired
	private NotificationService notiService;
	

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
    public ModelAndView GetMapping(HttpServletRequest request, Model model) throws CrudException{
		DecimalFormat decimalFormat = new DecimalFormat("#,###,###,###");

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");
       
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime dateTime = LocalDateTime.parse(paymentTime, formatter);
        
        String formattedPaymentTime = dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        int paymentStatus =vnPayService.orderReturn(request);
        if(paymentStatus == 1 && orderInfo.equalsIgnoreCase("nap tien")) {
        	WalletDTO dto = accService.getWalletByAccountID(accountIDLocal);
        	walletService.updateBalance(dto.getWalletID(),amount);
        	
        	TransactionHistoryRequestEntity entity = new TransactionHistoryRequestEntity();
        	entity.setAccountID(accountIDLocal);
        	entity.setAmount(amount);
        	String amountFormated = decimalFormat.format(amount);
        	entity.setDescription("Nạp " + amountFormated + " từ ngân hàng NCB");
        	entity.setTransactionType("Nạp tiền");       	
        	transService.createTransactionHistory(entity);      
        }
        if(paymentStatus == 1 && orderInfo.equalsIgnoreCase("rut tien")) {
        	WalletDTO dto = accService.getWalletByAccountID(accountIDLocal);
        	walletService.updateBalanceAfterStuff(dto.getWalletID(),amount);
        	
        	TransactionHistoryRequestEntity entity = new TransactionHistoryRequestEntity();
        	entity.setAccountID(accountIDLocal);
        	entity.setAmount(amount);
        	String amountFormated = decimalFormat.format(amount);
        	entity.setDescription("Rút tiền từ tài khoản về ngân hàng NCB: -" + amountFormated + " VND.");
        	entity.setTransactionType("Rút tiền");       	
        	transService.createTransactionHistory(entity);      	
        }
        

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", formattedPaymentTime);
        model.addAttribute("transactionId", transactionId);

        String viewName = paymentStatus == 1 ? "ordersuccess.html" : "orderfail.html";

        return new ModelAndView(viewName);
    }
}
