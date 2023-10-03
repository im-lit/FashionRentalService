package com.example.fashionrentalservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fashionrentalservice.model.dto.RoleDTO;

public interface RoleRepository extends JpaRepository<RoleDTO, Integer>{

}
