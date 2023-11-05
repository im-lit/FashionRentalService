package com.example.fashionrentalservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.example.fashionrentalservice.model.dto.product.VoucherTypeDTO;

@Repository
public interface VoucherTypeRepository extends JpaRepository<VoucherTypeDTO, Integer>{

}
