package com.example.fashionrentalservice.model.dto.order;

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

import com.example.fashionrentalservice.model.dto.account.AccountDTO;
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
@Table(name = "tbl_rentalproductimg")
public class RentalProductImgDTO {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rentalproductimg_id", columnDefinition = "INT")
	private int rentalProductImgID;
	
	private String productImg;
	
	private LocalDate createdDate;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
	@JoinColumn(name = "account_id")
	@JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
	private AccountDTO accountDTO;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
	@JoinColumn(name = "orderrent_id")
	@JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
	private OrderRentDTO orderRentDTO;

}
