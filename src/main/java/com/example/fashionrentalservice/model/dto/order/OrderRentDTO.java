package com.example.fashionrentalservice.model.dto.order;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.lang.Nullable;

import com.example.fashionrentalservice.model.dto.account.CustomerDTO;
import com.example.fashionrentalservice.model.dto.account.ProductOwnerDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_orderrent")
public class OrderRentDTO {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderrent_id", columnDefinition = "INT")
	private int orderRentID;
	
	private double totalRentPriceProduct;
	
	private double shippingFee;

	private double total;
	
	private double cocMoneyTotal;
	
	private String customerAddress;
	
	private LocalDate dateOrder;
	
	@Nullable
	private String orderCode;
	
	
	@Enumerated(EnumType.STRING)
	private OrderRentStatus status;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
	@JoinColumn(name = "customer_id")
	@JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
	private CustomerDTO customerDTO;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
	@JoinColumn(name = "productowner_id")
	@JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
	private ProductOwnerDTO productownerDTO;

	public enum OrderRentStatus {
		PENDING, PREPARE, READY_PICKUP, CONFIRMING, RENTING, RETURNING, REJECTING, REJECTING_COMPLETED, CANCELED, COMPLETED
	}
}
