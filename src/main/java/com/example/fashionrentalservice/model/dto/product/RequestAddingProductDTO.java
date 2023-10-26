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
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
@Table(name = "tbl_addingproductrequest")
public class RequestAddingProductDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "addproduct_id", columnDefinition = "INT")
	private int requestAddingProductID;
	
	private LocalDate createdtDate;
	
	@Enumerated(EnumType.STRING)
	private AddProductStatus status;
	
	private String description;

	@OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "productid", unique = true)
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private ProductDTO productDTO;
	
    public enum AddProductStatus{
        APPROVING, APPROVED, NOT_APPROVED
    }
	
}
