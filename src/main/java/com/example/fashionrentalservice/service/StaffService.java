package com.example.fashionrentalservice.service;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fashionrentalservice.model.dto.account.StaffDTO;
import com.example.fashionrentalservice.model.request.StaffRequestEntity;
import com.example.fashionrentalservice.model.response.StaffResponseEntity;
import com.example.fashionrentalservice.repositories.StaffRepository;
@Service
public class StaffService {
	@Autowired
	private StaffRepository staffRepo;
	
	//================================== Lay tat ca Staff ========================================
	public List<StaffResponseEntity> getAllStaff() {
		return  staffRepo.findAll().stream()
                .map(StaffResponseEntity::fromStaffDTO)
                .collect(Collectors.toList());
		
	}
	
	//================================== Tao Staff========================================
    public StaffResponseEntity createStaff(StaffRequestEntity entity) {
    	StaffDTO dto = StaffDTO.builder()
                .fullName(entity.getFullName())
                .status(entity.isStatus())
                .avatarUrl(entity.getAvatarUrl())
                .build();

        return StaffResponseEntity.fromStaffDTO(staffRepo.save(dto));
    }
  //================================== Update status Staff========================================
    public StaffResponseEntity updateStatusStaff(int staffID, boolean status) {
        StaffDTO dto = staffRepo.findById(staffID).orElseThrow();
        dto.setStatus(status);
        ;
        return StaffResponseEntity.fromStaffDTO(staffRepo.save(dto));
    }
    
    //================================== Lấy Staff bởi ID========================================    
	public StaffResponseEntity getStaffByID(int staffID) {
		return StaffResponseEntity.fromStaffDTO(staffRepo.findById(staffID).orElseThrow());
	}
}
