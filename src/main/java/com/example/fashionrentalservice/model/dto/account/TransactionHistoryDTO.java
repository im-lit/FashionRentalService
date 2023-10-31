package com.example.fashionrentalservice.model.dto.account;

import java.time.LocalDate;

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
@Table(name = "tbl_transactionhistory")
public class TransactionHistoryDTO {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transactionhistory_id")
	private int transactionHistoryID;
	
	@Column(columnDefinition = "nvarchar(100)", nullable = true)
	private String transactionType;
	
	private double amount;
	
	private String description;
	
	private LocalDate transactionDate;
	
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private AccountDTO accountDTO;
	
}
