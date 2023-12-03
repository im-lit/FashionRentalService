package com.example.fashionrentalservice.model.dto.product;

import java.time.LocalDate;
import java.util.List;

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

import com.example.fashionrentalservice.model.dto.account.CustomerDTO;
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
@Table(name = "tbl_feedback")
public class FeedBackDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "feedback_id",unique = true, columnDefinition = "INT")
	private int feedbackID;
	
	@Column(columnDefinition = "nvarchar(500)")
	private String description;
	
	private String imgUrl;
	
	private LocalDate createdtDate;
	private int ratingPoint;
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id")
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private CustomerDTO customerDTO;
	
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "productid")
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private ProductDTO productDTO;
	
	
}
