package com.example.fashionrentalservice.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
@Table(name = "tbl_productowner")
public class ProductOwnerDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productowner_id", columnDefinition = "INT")
	private int productownerID;
	
    @Column(name = "full_name", columnDefinition = "nvarchar(255)", nullable = true)
	private String fullName;
	
	private String phone;
	
	private boolean status;
	
	private String avatarUrl;
	
	private double balance;
	
	@Column(name = "address", columnDefinition = "nvarchar(255)", nullable = true)
	private String address;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "account_id")
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private AccountDTO account;
}
