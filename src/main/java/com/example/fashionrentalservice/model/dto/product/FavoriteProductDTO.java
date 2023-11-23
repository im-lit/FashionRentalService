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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.fashionrentalservice.model.dto.account.CustomerDTO;
import com.example.fashionrentalservice.model.dto.account.ProductOwnerDTO;
import com.example.fashionrentalservice.model.dto.product.VoucherDTO.VoucherStatus;
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
@Table(name = "tbl_favoriteproduct")
public class FavoriteProductDTO {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favoriteproduct_id", unique = true)
	private int favoriteProductID;
	
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "productid")
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private ProductDTO productDTO;
	
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private CustomerDTO customerDTO;
	
	@Enumerated(EnumType.STRING)
	private FavoriteStatus status;
	
	public enum FavoriteStatus {
		ACTIVE, INACTIVE
	}
}
