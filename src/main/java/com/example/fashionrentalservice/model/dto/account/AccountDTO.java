package com.example.fashionrentalservice.model.dto.account;




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
@Table(name = "tbl_account" )
public class AccountDTO {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
	private int accountID;
	
	
	private String password;
	
	private String email;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleID", columnDefinition = "INT")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private RoleDTO roleDTO;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", unique = true, referencedColumnName = "customer_id")
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private CustomerDTO customerDTO;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", unique = true, referencedColumnName = "productowner_id")
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private ProductOwnerDTO productOwnerDTO;
	
	
	
}
