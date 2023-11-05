package com.example.fashionrentalservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.example.fashionrentalservice.model.dto.product.VoucherDTO;


@Repository
public interface VoucherRepository extends JpaRepository<VoucherDTO, Integer> {
	@Query("select dto from VoucherDTO dto where dto.productOwnerDTO.productownerID = ?1")
	List<VoucherDTO> findByProductOwnerID(int productownerID);
}
