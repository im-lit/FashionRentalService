package com.example.fashionrentalservice.model.dto.product;

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

import com.example.fashionrentalservice.model.dto.account.ProductOwnerDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "tbl_product")
public class ProductDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "productid", columnDefinition = "INT")
	private int productID;

	@Column(columnDefinition = "nvarchar(255)")
	private String productName;

	private String productReceiptUrl;
	
	private String productAvt;

	private String description;

	private double price;

	@Enumerated(EnumType.STRING)
	private ProductStatus status;

	private boolean forSale;

	private boolean forRent;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "categoryID")
	@JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
	private CategoryDTO category;
	
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
	@JoinColumn(name = "productowner_id")
	@JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
	private ProductOwnerDTO productownerDTO;

	@JsonIgnore
	@OneToOne(mappedBy = "productDTO", cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private ProductRentalPricesDTO productRentalPricesDTO;
	
	@JsonIgnore
	@OneToOne(mappedBy = "productDTO", cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private RequestAddingProductDTO requestAddingProductDTO;

	@Column(columnDefinition = "json")
	private String productSpecificationData;

	public enum ProductStatus {
		BLOCKED, WAITING, CAN_SALE, CAN_RENT, CAN_SALE_RENT,RENTING, SOLD_OUT
	}
}
