//package com.example.fashionrentalservice.model.dto.order;
//
//import java.time.LocalDate;
//
//import javax.persistence.Entity;
//import javax.persistence.Table;
//
//import com.example.fashionrentalservice.model.dto.account.CustomerDTO;
//import com.example.fashionrentalservice.model.dto.account.ProductOwnerDTO;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@Entity
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Table(name = "tbl_orderbuy")
//public class OrderBuy {
//
//	private int orderBuyID;
//
//	private double total;
//	
//	private LocalDate dateOrder;
//
//	private OrderBuyStatus status;
//
//	private CustomerDTO customerDTO;
//
//	private ProductOwnerDTO productownerDTO;
//
//	public enum OrderBuyStatus {
//		BLOCKED, VERIFIED, NOT_VERIFIED
//	}
//}
