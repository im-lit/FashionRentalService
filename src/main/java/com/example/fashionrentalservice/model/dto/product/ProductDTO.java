package com.example.fashionrentalservice.model.dto.product;

import java.util.List;

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
import javax.persistence.OneToMany;
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
	@Column(name = "productid",unique = true, columnDefinition = "INT")
	private int productID;

	@Column(columnDefinition = "nvarchar(255)")
	private String productName;

	private String productReceiptUrl;
	
	private String productAvt;
	
	@Column(columnDefinition = "nvarchar(500)")
	private String description;
	
	@Column(columnDefinition = "nvarchar(2000)")
	private String term;
	
	private String serialNumber;
	
	private double price;
	
	@Column(name = "product_condition",columnDefinition = "nvarchar(255)")
	private String productCondition;
 
	@Enumerated(EnumType.STRING)
	private ProductStatus status;

	@Enumerated(EnumType.STRING)
	private checkTypeSaleorRentorSaleRent checkType;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "categoryID")
	@JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
	private CategoryDTO category;
	
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
	@JoinColumn(name = "productowner_id")
	@JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
	private ProductOwnerDTO productownerDTO;
	
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
	@JoinColumn(name = "productid")
	@JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
	private List<ProductRentalPricesDTO> rentprices;
	
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
	@JoinColumn(name = "productid")
	@JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
	private List<ProductDetailDTO> detailDTO;

	
	@JsonIgnore
	@OneToOne(mappedBy = "productDTO", cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private RequestAddingProductDTO requestAddingProductDTO;

	@Column(columnDefinition = "json")
	private String productSpecificationData;

	public enum ProductStatus {
		BLOCKED, WAITING, AVAILABLE, RENTING, SOLD_OUT
	}
	public enum checkTypeSaleorRentorSaleRent {
		SALE, RENT, SALE_RENT
   }
}