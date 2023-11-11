package com.example.fashionrentalservice.model.dto.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_productowner")
public class ProductOwnerDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productowner_id",unique = true, columnDefinition = "INT")
	private int productownerID;
	
    @Column(name = "full_name", columnDefinition = "nvarchar(255)", nullable = true)
	private String fullName;
	
	private String phone;
	
	@Nullable
	private String POToken;
	
	@Nullable
	private int POShopID;
	
	private String avatarUrl;
	
	
	@Column(name = "address", columnDefinition = "nvarchar(255)", nullable = true)
	private String address;
	
	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "account_id")
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private AccountDTO accountDTO;
}
