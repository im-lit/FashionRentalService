package com.example.fashionrentalservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.example.fashionrentalservice.model.dto.account.WalletDTO;

@EnableJpaRepositories
@Repository
public interface WalletRepository extends JpaRepository<WalletDTO, Integer>{
	@Query("select dto from WalletDTO dto where dto.accountDTO.accountID = ?1")
	WalletDTO findByAccountID(int accountID);

}