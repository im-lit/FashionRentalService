package com.example.fashionrentalservice.model.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_address")
public class AddressDTO {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "addressid")
	private int addressID;
	
	
	@Column(name = "address", columnDefinition = "nvarchar(255)", nullable = true)
	private String addressDescription;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerID")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private CustomerDTO customerDTO;
	
}
