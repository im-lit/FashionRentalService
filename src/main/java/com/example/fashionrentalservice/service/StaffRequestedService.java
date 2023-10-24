package com.example.fashionrentalservice.service;


import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fashionrentalservice.model.dto.product.StaffRequestedDTO;
import com.example.fashionrentalservice.repositories.RequestAddingProductRepository;
import com.example.fashionrentalservice.repositories.StaffRepository;
import com.example.fashionrentalservice.repositories.StaffRequestedRepository;
@Service
public class StaffRequestedService {
	@Autowired
	private StaffRequestedRepository staffRequestedRepo;
	
	@Autowired
	private RequestAddingProductRepository addRequestedRepo;
	
	@Autowired
	private StaffRepository staffRepository;
	
	
	//================================== Lay tat ca yêu cầu mà Staff đã duyệt ========================================
	public List<StaffRequestedDTO> getAllStaffRequested() {
		List<StaffRequestedDTO> list = staffRequestedRepo.findAll();
		return  list;
		
	}
	//================================== Tao Request========================================
    public StaffRequestedDTO create(int requestAddingProductID, int staffID) {
    	StaffRequestedDTO dto = StaffRequestedDTO.builder()
    			.createdDate(LocalDate.now())
    			.requestAddingProductDTO(addRequestedRepo.findById(requestAddingProductID).orElseThrow())
    			.staffDTO(staffRepository.findById(staffID).orElseThrow())
                .build();
        return staffRequestedRepo.save(dto);

}
}
