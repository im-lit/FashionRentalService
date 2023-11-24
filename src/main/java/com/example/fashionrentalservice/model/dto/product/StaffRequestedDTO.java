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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.fashionrentalservice.model.dto.account.StaffDTO;
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
@Table(name = "tbl_staffrequested")
public class StaffRequestedDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "staffrequested_id", columnDefinition = "INT")
	private int staffRequestedID;
	
	private LocalDate createdDate;
	

	@OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "addproduct_id", unique = true)
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private RequestAddingProductDTO requestAddingProductDTO;
	
	@OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "requestcomplainingorder_id", unique = true)
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private RequestComplainingOrderDTO requestComplainingOrderDTO;
	
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	@JoinColumn(name = "staff_id")
	@JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
	private StaffDTO staffDTO;
	
}
