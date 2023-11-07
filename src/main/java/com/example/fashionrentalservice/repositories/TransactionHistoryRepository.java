package com.example.fashionrentalservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.example.fashionrentalservice.model.dto.account.TransactionHistoryDTO;

@EnableJpaRepositories
@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistoryDTO, Integer>{
	
	@Query("select dto from TransactionHistoryDTO dto  where dto.accountDTO.accountID = ?1 ORDER BY dto.transactionHistoryID DESC")
	List<TransactionHistoryDTO> findAllByAccountID(int accountID);
	
}