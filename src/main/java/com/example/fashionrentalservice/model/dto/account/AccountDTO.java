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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
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
	
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roleID", columnDefinition = "INT")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private RoleDTO roleDTO;
	
	@JsonIgnore
	@OneToOne(mappedBy = "accountDTO",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private CustomerDTO customerDTO;
	
	@JsonIgnore
	@OneToOne(mappedBy = "accountDTO", cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private ProductOwnerDTO productOwnerDTO;
	
	@JsonIgnore
	@OneToOne(mappedBy = "accountDTO", cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private StaffDTO staffDTO;
	
	
	
}
