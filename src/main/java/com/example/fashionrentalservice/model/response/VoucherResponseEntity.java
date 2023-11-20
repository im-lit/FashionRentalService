package com.example.fashionrentalservice.model.response;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.example.fashionrentalservice.model.dto.product.VoucherDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoucherResponseEntity {

		    
	
	private int voucherID;
    
	private String voucherCode;
 
	private String voucherName;
 	
 	private LocalDate createdDate;
 	private LocalDate startDate;
	
 	private LocalDate endDate;
	
 	
	private String description;
 	
 	
	private int maxDiscount;
 	
 	private int quantity;
	private int discountAmount;
	private int voucherTypeID;
	private int productOwnerID;
	//private VoucherTypeDTO voucherTypeDTO;
	//private ProductOwnerDTO productOwnerDTO;
 	private String status;
	    
		public static VoucherResponseEntity fromVoucherDTO(VoucherDTO dto) {
			return VoucherResponseEntity.builder()
					.voucherID(dto.getVoucherID())
					.voucherCode(dto.getVoucherCode())
					.voucherName(dto.getVoucherName())
					.createdDate(dto.getCreatedDate())
					.startDate(dto.getStartDate())
					.endDate(dto.getEndDate())
					.description(dto.getDescription())
					.maxDiscount(dto.getMaxDiscount())
					.quantity(dto.getQuantity())
					.discountAmount(dto.getDiscountAmount())
					.status(dto.getStatus().toString())
					.voucherTypeID(dto.getVoucherTypeDTO().getVoucherTypeID())
					.productOwnerID(dto.getProductOwnerDTO().getProductownerID())
					.build();
		}
		public static List<VoucherResponseEntity> fromListVoucherDTO(List<VoucherDTO> dtos) {
			return dtos.stream()
		            .map(dto -> VoucherResponseEntity.builder()
		            		.voucherID(dto.getVoucherID())
							.voucherCode(dto.getVoucherCode())
							.voucherName(dto.getVoucherName())
							.createdDate(dto.getCreatedDate())
							.startDate(dto.getStartDate())
							.endDate(dto.getEndDate())
							.description(dto.getDescription())
							.quantity(dto.getQuantity())
							.maxDiscount(dto.getMaxDiscount())
							.discountAmount(dto.getDiscountAmount())
							.status(dto.getStatus().toString())
							.voucherTypeID(dto.getVoucherTypeDTO().getVoucherTypeID())
							.productOwnerID(dto.getProductOwnerDTO().getProductownerID())
							.build())
		            .collect(Collectors.toList());
		}
}
