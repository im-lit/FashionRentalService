package com.example.fashionrentalservice.model.dto.product;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import com.example.fashionrentalservice.model.dto.account.ProductOwnerDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_voucher")
public class VoucherDTO {

	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "voucher_id")
		private int voucherID;
	    
		private String voucherCode;
	 	
	 	@Column(columnDefinition = "nvarchar(255)")
		private String voucherName;
	 	
	 	private LocalDate createdtDate;
	 	private LocalDate startDate;
		
	 	private LocalDate endDate;
		
	 	@Column(columnDefinition = "nvarchar(255)")
		private String description;
	 	
	 	
		private int maxDiscount;
	 	
		private int discountAmount;
	 	
	 	private boolean status;
	 	
	 	
		@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
		@JoinColumn(name = "vouchertype_id")
		@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
		private VoucherTypeDTO voucherTypeDTO;
	 	
		
		@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
		@JoinColumn(name = "productowner_id")
		@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
		private ProductOwnerDTO productOwnerDTO;
}
