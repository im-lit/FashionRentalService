package com.example.fashionrentalservice.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fashionrentalservice.exception.AccNotFoundByID;
import com.example.fashionrentalservice.exception.PendingMoneyNegative;
import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.dto.product.RequestAddingProductDTO;
import com.example.fashionrentalservice.model.dto.product.RequestAddingProductDTO.AddProductStatus;
import com.example.fashionrentalservice.model.dto.product.StaffRequestedDTO;
import com.example.fashionrentalservice.model.request.AddingProductRequestEntity;
import com.example.fashionrentalservice.model.response.AddingProductResponseEntity;
import com.example.fashionrentalservice.repositories.ProductRepository;
import com.example.fashionrentalservice.repositories.RequestAddingProductRepository;
import com.example.fashionrentalservice.repositories.StaffRequestedRepository;

@Service
public class RequestAddingProductService {

	@Autowired
	private RequestAddingProductRepository requestAddRepo;
	
	@Autowired
	private StaffRequestedRepository staffRequestedRepo;
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private NotificationService notiService;
	
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
    	StaffRequestedDTO check = staffRequestedRepo.findRequestAddProduct(requestID);
    	if(check != null) {
    		throw new PendingMoneyNegative("Đơn này đã được duyệt !");
    	}
    	dto.setDescription(description);
        dto.setStatus(status);
        notiService.pushNotification(dto.getProductDTO().getProductownerDTO().getAccountDTO().getAccountID(), "Duyệt sản phẩm", "Sản phẩm: " + dto.getProductDTO().getProductName() + " đã được duyệt.");
        	return AddingProductResponseEntity.fromRequestAddingProductDTO(requestAddRepo.save(dto));
    }
        

//================================== Lay tat ca Request========================================
	public List<AddingProductResponseEntity> getApprovingRequest() {
		return requestAddRepo.findApprovingRequest().stream().map(AddingProductResponseEntity::fromRequestAddingProductDTO).collect(Collectors.toList());

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
	
	
