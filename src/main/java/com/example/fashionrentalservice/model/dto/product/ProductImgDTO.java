package com.example.fashionrentalservice.model.dto.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "tbl_productimg")
public class ProductImgDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "productimg_id", columnDefinition = "INT")
	private int productImgID;
	
	private String imgUrl;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "productID")
	@JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
	private ProductDTO productDTO;
	
}
