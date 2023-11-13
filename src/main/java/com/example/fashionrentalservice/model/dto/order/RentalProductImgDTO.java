//package com.example.fashionrentalservice.model.dto.order;
//
//import java.time.LocalDate;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//
//import org.springframework.lang.Nullable;
//
//import com.example.fashionrentalservice.model.dto.account.CustomerDTO;
//import com.example.fashionrentalservice.model.dto.account.ProductOwnerDTO;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
//@Table(name = "tbl_rentalproductimg")
//public class RentalProductImgDTO {
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "rentalproductimg_id", columnDefinition = "INT")
//	private int rentalProductImgID;
//	
//	private String productImg;
//	
//	
//	private LocalDate createdDate;
//	
//	@Nullable
//	private String orderCode;
//	
//	@Enumerated(EnumType.STRING)
//	private OrderBuyStatus status;
//	
//	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
//	@JoinColumn(name = "customer_id")
//	@JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
//	private CustomerDTO customerDTO;
//	
//	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
//	@JoinColumn(name = "productowner_id")
//	@JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
//	private ProductOwnerDTO productownerDTO;
//
//	public enum OrderBuyStatus {
//		PENDING, PREPARE, READY_PICKUP, CONFIRMING, REJECTING, REJECTING_COMPLETED,CANCELED, COMPLETED
//	}
//}