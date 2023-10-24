package com.example.fashionrentalservice.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fashionrentalservice.exception.AccNotFoundByID;
import com.example.fashionrentalservice.exception.EmailExisted;
import com.example.fashionrentalservice.exception.LoginFail;
import com.example.fashionrentalservice.exception.UpdatePasswordFail;
import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.dto.account.AccountDTO;
import com.example.fashionrentalservice.model.dto.account.AccountDTO.AccountStatus;
import com.example.fashionrentalservice.model.dto.product.RequestAddingProductDTO;
import com.example.fashionrentalservice.model.dto.product.RequestAddingProductDTO.AddProductStatus;
import com.example.fashionrentalservice.model.request.AccountRequestEntity;
import com.example.fashionrentalservice.model.request.AddingProductRequestEntity;
import com.example.fashionrentalservice.model.response.AccountResponseEntity;
import com.example.fashionrentalservice.model.response.AddingProductResponseEntity;
import com.example.fashionrentalservice.repositories.AccountRepository;
import com.example.fashionrentalservice.repositories.ProductRepository;
import com.example.fashionrentalservice.repositories.RequestAddingProductRepository;
import com.example.fashionrentalservice.repositories.RoleRepository;

@Service
public class RequestAddingProductService {

	@Autowired
	private RequestAddingProductRepository requestAddRepo;
	
	@Autowired
	private ProductRepository productRepo;
	
//================================== Tạo mới Request ========================================
    public AddingProductResponseEntity createRequest(AddingProductRequestEntity entity) throws CrudException{
        RequestAddingProductDTO dto = RequestAddingProductDTO.builder()
                .description("")
                .createdtDate(LocalDate.now())
                .status(AddProductStatus.APPROVING)
                .productDTO(productRepo.findById(entity.getProductID()).orElseThrow())
                .build();
       
        return AddingProductResponseEntity.fromRequestAddingProductDTO(requestAddRepo.save(dto));
    }
  //================================== Update Request Status Description ========================================   
    public AddingProductResponseEntity updateRequestStatusAndDes(int requestID,AddProductStatus status, String description) throws CrudException {
    	RequestAddingProductDTO dto = requestAddRepo.findById(requestID).orElseThrow();
    	dto.setDescription(description);
        dto.setStatus(status);    
        	return AddingProductResponseEntity.fromRequestAddingProductDTO(requestAddRepo.save(dto));
    }
        

//================================== Lay tat ca Request========================================
	public List<AddingProductResponseEntity> getAllRequest() {
		return requestAddRepo.findAll().stream().map(AddingProductResponseEntity::fromRequestAddingProductDTO).collect(Collectors.toList());

	}
//================================== Lay account bởi ID========================================	
	public AddingProductResponseEntity getRequestByID(int reqeustID) throws CrudException{
		RequestAddingProductDTO dto = requestAddRepo.findById(reqeustID).orElse(null);
		if(dto==null) 
			throw new AccNotFoundByID();
		return AddingProductResponseEntity.fromRequestAddingProductDTO(dto);
		}
//================================== Xóa Account========================================
    public AddingProductResponseEntity deleteExistedRequest(int requestID) {
    	RequestAddingProductDTO dto = requestAddRepo.findById(requestID).orElseThrow();
    	requestAddRepo.deleteById(requestID);
        return AddingProductResponseEntity.fromRequestAddingProductDTO(dto);
    }
	
}
	
	
