package com.example.fashionrentalservice.model.dto.order;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.fashionrentalservice.model.dto.product.ProductDTO;
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
@Table(name = "tbl_orderrentdetail")
public class OrderRentDetailDTO {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderrentdetail_id", columnDefinition = "INT")
	private int orderRentDetailID;
	
	private double cocMoney;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private double rentPrice;
	
	private long remainingDate;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "productid")
	@JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
	private ProductDTO productDTO;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orderrent_id")
	@JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
	private OrderRentDTO orderRentDTO;
	
}
