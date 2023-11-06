package com.example.fashionrentalservice.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.fashionrentalservice.exception.DaysBetweenTwoDays;
import com.example.fashionrentalservice.exception.StaffNotFoundByID;
import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.dto.account.AccountDTO;
import com.example.fashionrentalservice.model.dto.account.WalletDTO;
import com.example.fashionrentalservice.model.dto.account.AccountDTO.AccountStatus;
import com.example.fashionrentalservice.model.dto.product.ProductDTO;
import com.example.fashionrentalservice.model.dto.product.VoucherDTO;
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


@Service
public class VoucherService {
	
	
	@Autowired
	private VoucherRepository voucherRepo;
	
	@Autowired
	private VoucherTypeRepository voucherTypeRepo;
	@Autowired
	private ProductOwnerRepository poRepo;
	
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
		
		return VoucherResponseEntity.fromListVoucherDTO(dto);
	
	}
	//================================== List All Voucher ========================================
		public List<VoucherResponseEntity> getVoucherByProductOwnerIDNotExpired(int productowner) throws  CrudException {
			List<VoucherDTO> dto = voucherRepo.findByProductOwnerID(productowner);
			if(dto==null) {
				throw new StaffNotFoundByID();
			}
			List<VoucherDTO> expiredList = new ArrayList<>();
			for (VoucherDTO x : dto) {
				long daysBetween = ChronoUnit.DAYS.between(x.getStartDate(), x.getEndDate());
				if(daysBetween < 0) 
				expiredList.add(x);
			}
			dto.removeIf(expiredList::contains);
			return VoucherResponseEntity.fromListVoucherDTO(dto);
		}

	//================================== Create Voucher ========================================
	public VoucherResponseEntity createVoucher(VoucherRequestEntity entity) throws  CrudException {
			
		VoucherDTO dto = VoucherDTO.builder()
				.voucherCode(entity.getVoucherCode())
				.voucherName(entity.getVoucherName())
				.createdtDate(LocalDate.now())
				.startDate(entity.getStartDate())
				.endDate(entity.getEndDate())
				.description(entity.getDescription())
				.discountAmount(entity.getDiscountAmount())
				.maxDiscount(entity.getMaxDiscount())
				.status(entity.isStatus())
				.productOwnerDTO(poRepo.findById(entity.getProductownerID()).orElse(null))
				.voucherTypeDTO(voucherTypeRepo.findById(entity.getVoucherTypeID()).orElse(null))
				.build();
		long daysBetween = ChronoUnit.DAYS.between(entity.getStartDate(), entity.getEndDate());
		if(daysBetween < 0) 
			throw new DaysBetweenTwoDays();
		return  VoucherResponseEntity.fromVoucherDTO(voucherRepo.save(dto));	
	}
	
	//================================== Update Status Voucher ========================================
    public VoucherResponseEntity updateStatusVoucherByVoucherID(int voucherID,Boolean status) throws CrudException {
        VoucherDTO dto = voucherRepo.findById(voucherID).orElseThrow();
        dto.setStatus(status);    
        	return VoucherResponseEntity.fromVoucherDTO(voucherRepo.save(dto));
    }

	
}
