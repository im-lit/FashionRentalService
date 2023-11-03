package com.example.fashionrentalservice.model.dto.order;

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
@Table(name = "tbl_orderbuydetail")
public class OrderBuyDetailDTO {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderbuydetail_id", columnDefinition = "INT")
	private int orderBuyDetailID;
	
	private double price;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productid")
	@JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
	private ProductDTO productDTO;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orderbuy_id")
	@JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
	private OrderBuyDTO orderBuyDTO;
	
}
