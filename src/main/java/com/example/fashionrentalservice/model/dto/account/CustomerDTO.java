package com.example.fashionrentalservice.model.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "tbl_customer")
public class CustomerDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", columnDefinition = "INT")
	private int customerID;
	
    @Column(name = "full_name", columnDefinition = "nvarchar(255)", nullable = true)
	private String fullName;
	
	private String phone;
	
	private boolean sex;
	
	private boolean status;
	
	private String avatarUrl;
	
	private double balance;
	
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id", unique = true)
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private AccountDTO accountDTO;

}
