package com.example.fashionrentalservice.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.fashionrentalservice.exception.DaysBetweenTwoDays;
import com.example.fashionrentalservice.exception.PendingMoneyNegative;
import com.example.fashionrentalservice.exception.StaffNotFoundByID;
import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.dto.account.AccountDTO;
import com.example.fashionrentalservice.model.dto.account.WalletDTO;
import com.example.fashionrentalservice.model.dto.account.AccountDTO.AccountStatus;
import com.example.fashionrentalservice.model.dto.product.ProductDTO;
import com.example.fashionrentalservice.model.dto.product.VoucherDTO;
import com.example.fashionrentalservice.model.dto.product.VoucherDTO.VoucherStatus;
import com.example.fashionrentalservice.model.dto.product.ProductDTO.ProductStatus;
import com.example.fashionrentalservice.model.request.VoucherRequestEntity;
import com.example.fashionrentalservice.model.request.WalletRequestEntity;
import com.example.fashionrentalservice.model.response.AccountResponseEntity;
import com.example.fashionrentalservice.model.response.CustomerResponseEntity;
import com.example.fashionrentalservice.model.response.ProductImgResponseEntity;
import com.example.fashionrentalservice.model.response.ProductResponseEntity;
import com.example.fashionrentalservice.model.response.VoucherResponseEntity;
import com.example.fashionrentalservice.model.response.WalletResponseEntity;
import com.example.fashionrentalservice.repositories.ProductOwnerRepository;
import com.example.fashionrentalservice.repositories.VoucherRepository;
import com.example.fashionrentalservice.repositories.VoucherTypeRepository;
import com.google.common.math.Quantiles;


@Service
public class VoucherService {
	
	
	@Autowired
	private VoucherRepository voucherRepo;
	
	@Autowired
	private VoucherTypeRepository voucherTypeRepo;
	@Autowired
	private ProductOwnerRepository poRepo;
	private static final String BLANK_PATTERN = "^\\S.*$";
	public List<VoucherResponseEntity> getAllVoucher() {
		return  voucherRepo.findAll().stream()
                .map(VoucherResponseEntity::fromVoucherDTO)
                .collect(Collectors.toList());
		
	}
	
	//================================== List All Voucher cua 1 PO ========================================
	public List<VoucherResponseEntity> getVoucherByProductOwnerID(int productownerID) throws CrudException {
		List<VoucherDTO> dto = voucherRepo.findByProductOwnerID(productownerID);
		if(dto==null) 
			throw new StaffNotFoundByID();
		for (VoucherDTO x : dto)
		{
			LocalDate currentDate =LocalDate.now();
		    long daysBetween = ChronoUnit.DAYS.between(currentDate, x.getEndDate());
		//	long daysBetween = ChronoUnit.DAYS.between(x.getStartDate(), x.getEndDate());
			if( daysBetween < 0 ) {
				updateStatusVoucherByVoucherID(x.getVoucherID());
			}
			
		}
		return VoucherResponseEntity.fromListVoucherDTO(dto);
	
	}
	//================================== List All Voucher ========================================
		public List<VoucherResponseEntity> getVoucherByProductOwnerIDNotExpired(int productowner) throws  CrudException {
			List<VoucherDTO> dto = voucherRepo.findVoucherByProductOwnerID(productowner);
			
			if(dto==null) {
				throw new StaffNotFoundByID();
			}
			
			for (VoucherDTO x : dto)
			{
				LocalDate currentDate =LocalDate.now();
			    long daysBetween = ChronoUnit.DAYS.between(currentDate, x.getEndDate());
			//	long daysBetween = ChronoUnit.DAYS.between(x.getStartDate(), x.getEndDate());
				if(daysBetween < 0) 
					updateStatusVoucherByVoucherID(x.getVoucherID());
			}
			return VoucherResponseEntity.fromListVoucherDTO(dto);
		}

	//================================== Create Voucher ========================================
	public VoucherResponseEntity createVoucher(VoucherRequestEntity entity) throws  CrudException {
			
		if(!isValidProductName(entity.getVoucherName())) {
			throw new PendingMoneyNegative("Quantity must be greater or equal 0");
		}
		VoucherDTO dto = VoucherDTO.builder()
				.voucherCode(entity.getVoucherCode())
				.voucherName(entity.getVoucherName())
				.createdDate(LocalDate.now())
				.startDate(entity.getStartDate())
				.endDate(entity.getEndDate())
				.description(entity.getDescription())
				.discountAmount(entity.getDiscountAmount())
				.maxDiscount(entity.getMaxDiscount())
				.quantity(entity.getQuantity())
				.status(VoucherStatus.ACTIVE)
				.productOwnerDTO(poRepo.findById(entity.getProductownerID()).orElse(null))
				.voucherTypeDTO(voucherTypeRepo.findById(entity.getVoucherTypeID()).orElse(null))
				.build();
		VoucherDTO dtos =voucherRepo.findByVoucherCodeAndProductOwnerID(entity.getVoucherCode(), entity.getProductownerID());
		
		
		if((dto.getQuantity()<=0)) {
			throw new PendingMoneyNegative("Quantity must be greater or equal 0");
		}else if(dtos!=null){
			throw new PendingMoneyNegative("This code already existed");
		}
		long daysBetween = ChronoUnit.DAYS.between(entity.getStartDate(), entity.getEndDate());
		if(daysBetween < 0) { 
			throw new DaysBetweenTwoDays();
		}
		return  VoucherResponseEntity.fromVoucherDTO(voucherRepo.save(dto));	
	}
	 private boolean isValidProductName(String productName) {
	        Pattern pattern = Pattern.compile(BLANK_PATTERN);
	        Matcher matcher = pattern.matcher(productName);
	        return matcher.matches();
	    }
	public VoucherResponseEntity useVoucher(String voucherCode) throws  CrudException {
		VoucherDTO dto = voucherRepo.findByVoucherCode(voucherCode);
		
		if(dto==null) {
			throw new PendingMoneyNegative("Can not find this Voucher Code");
		}
		
		if(dto.getQuantity()==0) {
			dto.setStatus(VoucherStatus.OUT_OF_STOCK);
			throw new PendingMoneyNegative("This voucher is out of stock");
		}else {
		dto.setQuantity(dto.getQuantity()-1);
		}
		return  VoucherResponseEntity.fromVoucherDTO(voucherRepo.save(dto));	
	}
	public VoucherResponseEntity cancelVoucherUsage(String voucherCode) throws  CrudException {
		VoucherDTO dto = voucherRepo.findByVoucherCode(voucherCode);
		
		if(dto==null) {
			throw new PendingMoneyNegative("Can not find this Voucher Code");
		}
		if(dto.getQuantity()==0) {
			dto.setStatus(VoucherStatus.OUT_OF_STOCK);
			throw new PendingMoneyNegative("This voucher is out of stock");
		}else {
		dto.setQuantity(dto.getQuantity()+1);
		}
		return  VoucherResponseEntity.fromVoucherDTO(voucherRepo.save(dto));	
	}
	
	
	//================================== Update Status Voucher ========================================
    public VoucherResponseEntity updateStatusVoucherByVoucherID(int voucherID) throws CrudException {
        VoucherDTO dto = voucherRepo.findById(voucherID).orElseThrow();
        if(dto==null) {
			throw new PendingMoneyNegative("Can not find voucher by ID");
		}
        else if(dto.getQuantity()<=0) {
        	dto.setStatus(VoucherStatus.OUT_OF_STOCK);
        }
        else if(dto.getStatus().equals(VoucherStatus.INACTIVE)) {
        	dto.setStatus(VoucherStatus.ACTIVE);
        }else if(dto.getStatus().equals(VoucherStatus.ACTIVE)) {
        	dto.setStatus(VoucherStatus.INACTIVE);
        }
        
        LocalDate currentDate =LocalDate.now();
	    long daysBetween = ChronoUnit.DAYS.between(currentDate, dto.getEndDate());
	//	long daysBetween = ChronoUnit.DAYS.between(x.getStartDate(), x.getEndDate());
		if(daysBetween < 0) {
			dto.setStatus(VoucherStatus.OUTDATE);
		}
        	return VoucherResponseEntity.fromVoucherDTO(voucherRepo.save(dto));
    }
    
    public VoucherResponseEntity deleteVoucherByID(int voucherID) throws CrudException{
    	VoucherDTO dto = voucherRepo.findById(voucherID).orElse(null);
    	if(dto==null) {
    		throw new PendingMoneyNegative("Can not find voucher by ID");
    	}
    	voucherRepo.deleteById(voucherID);    
    return VoucherResponseEntity.fromVoucherDTO(dto);
}
}



