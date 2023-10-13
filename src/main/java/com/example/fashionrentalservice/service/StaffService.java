package com.example.fashionrentalservice.service;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fashionrentalservice.exception.StaffNotFoundByID;
import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.dto.account.StaffDTO;
import com.example.fashionrentalservice.model.request.StaffRequestEntity;
import com.example.fashionrentalservice.model.response.StaffResponseEntity;
import com.example.fashionrentalservice.repositories.AccountRepository;
import com.example.fashionrentalservice.repositories.StaffRepository;
@Service
public class StaffService {
	@Autowired
	private StaffRepository staffRepo;
	
	@Autowired
	private AccountRepository accRepo;
	
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
                .accountDTO(accRepo.findById(entity.getAccountID()).orElseThrow())
                .build();

        return StaffResponseEntity.fromStaffDTO(staffRepo.save(dto));
    }
  //================================== Update status Staff========================================
    public StaffResponseEntity updateStatusStaff(int staffID, boolean status) {
        StaffDTO dto = staffRepo.findById(staffID).orElseThrow();
        dto.setStatus(status);
        return StaffResponseEntity.fromStaffDTO(staffRepo.save(dto));
    }
    
    //================================== Lấy Staff bởi ID========================================    
	public StaffResponseEntity getStaffByID(int staffID) throws CrudException{

		StaffDTO dto = staffRepo.findById(staffID).orElse(null);
		if(dto==null) 
			throw new StaffNotFoundByID();
		
		return StaffResponseEntity.fromStaffDTO(dto);
	}
	
    //================================== Xóa Staff bởi ID========================================  
    public StaffResponseEntity deleteExistedStaff(int id) {
        StaffDTO dto = staffRepo.findById(id).orElseThrow();
        staffRepo.deleteById(id);

        return StaffResponseEntity.fromStaffDTO(dto);
    }
}
