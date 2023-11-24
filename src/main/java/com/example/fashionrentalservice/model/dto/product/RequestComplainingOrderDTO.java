package com.example.fashionrentalservice.model.dto.product;

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

import com.example.fashionrentalservice.model.dto.account.ProductOwnerDTO;
import com.example.fashionrentalservice.model.dto.order.OrderBuyDTO;
import com.example.fashionrentalservice.model.dto.order.OrderRentDTO;
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
@Table(name = "tbl_requestcomplainingorder")
public class RequestComplainingOrderDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "requestcomplainingorder_id", unique = true,columnDefinition = "INT")
	private int requestComplainingOrderID;
	
	private LocalDate createdtDate;
	
	private double expectedCost;
	
	@Enumerated(EnumType.STRING)
	private ComplainingOrderStatus status;
	
	private String description;
	
	private String staffResponse;

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "orderbuy_id")
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private OrderBuyDTO orderBuyDTO;
	
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "orderrent_id")
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private OrderRentDTO orderRentDTO;
	
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "productowner_id")
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private ProductOwnerDTO productownerDTO;
	
    public enum ComplainingOrderStatus{
        APPROVING, APPROVED, NOT_APPROVED
    }
	
}
