package com.example.fashionrentalservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.example.fashionrentalservice.model.dto.account.AccountDTO;
import com.example.fashionrentalservice.model.dto.account.WalletDTO;

@EnableJpaRepositories
@Repository
public interface AccountRepository extends JpaRepository<AccountDTO, Integer>{
	@Query("select acc from AccountDTO acc where acc.email = ?1 and acc.password = ?2")
	AccountDTO checkLoginAccountByEmailAndPassword(String email,String password);
	
	@Query("select acc from AccountDTO acc where acc.email = ?1")
	AccountDTO findByEmail(String email);
	
	@Query("select dto from WalletDTO dto where dto.accountDTO.accountID = ?1")
	WalletDTO findWalletByAccountId(int accountID);

	AccountDTO findAccountDtoByAccountID(int id);
}