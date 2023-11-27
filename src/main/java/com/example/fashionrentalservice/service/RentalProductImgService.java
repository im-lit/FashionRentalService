package com.example.fashionrentalservice.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fashionrentalservice.exception.PendingMoneyNegative;
import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.dto.account.AccountDTO;
import com.example.fashionrentalservice.model.dto.order.OrderRentDTO;
import com.example.fashionrentalservice.model.dto.order.OrderRentDetailDTO;
import com.example.fashionrentalservice.model.dto.order.RentalProductImgDTO;
import com.example.fashionrentalservice.model.dto.order.RentalProductImgDTO.imgStatus;
import com.example.fashionrentalservice.model.request.RentalProductImgRequestEntity;
import com.example.fashionrentalservice.repositories.AccountRepository;
import com.example.fashionrentalservice.repositories.AddressRepository;
import com.example.fashionrentalservice.repositories.OrderRentRepository;
import com.example.fashionrentalservice.repositories.RentalProductimgRepository;

@Service
public class RentalProductImgService {

	@Autowired
	private AccountRepository accRepo;
	
	@Autowired
	private AddressRepository addressRepo;
	
	@Autowired
	private OrderRentRepository rentRepo;
	
	@Autowired
	private RentalProductimgRepository rentPicRepo;
	
	



//================================== Tạo mới Account ========================================
    public List<RentalProductImgDTO> createPicture(RentalProductImgRequestEntity entity) throws CrudException{
    	List<RentalProductImgDTO> list = new ArrayList<>();
    	AccountDTO check = accRepo.findById(entity.getAccountID()).orElse(null);
    	OrderRentDTO order = rentRepo.findById(entity.getOrderRentID()).orElse(null);
    	
    	if(check == null)
    		throw new PendingMoneyNegative("not found Account");
    	if(order == null)
    		throw new PendingMoneyNegative("not found OrderDetail");
    	
    	for (String x : entity.getImg()) {
    		RentalProductImgDTO dto = RentalProductImgDTO.builder()
        			.createdDate(LocalDate.now())
        			.productImg(x)
        			.status(imgStatus.valueOf(entity.getStatus()))
        			.accountDTO(check)
                    .orderRentDTO(order)
                    .build();
    		list.add(dto);
		}
    	
        return rentPicRepo.saveAll(list);
    }


    public RentalProductImgDTO deletePicByID(int picID) throws CrudException{
    	RentalProductImgDTO dto = rentPicRepo.findById(picID).orElse(null);
    	if(dto == null)
    		throw new PendingMoneyNegative("RentalProductImg not found");
    		
    	rentPicRepo.delete(dto);
    	
        return dto;
    }
    
    public List<RentalProductImgDTO> getAllRentalProductImgByAccountID (int accountID) throws CrudException{
    	List<RentalProductImgDTO> list = new ArrayList<>();
    	AccountDTO check = accRepo.findById(accountID).orElse(null);
    	if(check == null)
    		throw new PendingMoneyNegative("not found Account");
    	
    	list = rentPicRepo.findAllRentalProductImgByAccountID(accountID);
    	
    	return list;
    }
    
    
    
    
    public List<RentalProductImgDTO> getAllRentalProductImgByOrderRentID (int orderRentID) throws CrudException{
    	List<RentalProductImgDTO> list = new ArrayList<>();
    	OrderRentDTO order = rentRepo.findById(orderRentID).orElse(null);
    	if(order == null)
    		throw new PendingMoneyNegative("not found OrderDetail");
    	
    	list = rentPicRepo.findAllRentalProductImgByOrderRentID(orderRentID);
    	return list;
    }
}
	
	
