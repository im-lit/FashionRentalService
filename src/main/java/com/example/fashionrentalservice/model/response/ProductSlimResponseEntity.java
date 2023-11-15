package com.example.fashionrentalservice.model.response;

import java.util.List;

import com.example.fashionrentalservice.model.dto.product.CategoryDTO;
import com.example.fashionrentalservice.model.dto.product.ProductDTO;
import com.example.fashionrentalservice.model.dto.product.ProductRentalPricesDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSlimResponseEntity {
	private int productID;

	private String productName;
	
	private String productCondition;
	
	private String productAvt;
	
	private double price;

	private String checkType;
	
	private String status;
	
	private String productOwnerName;
	
	private String productSpecifications;
	
	private CategoryDTO categoryDTO;
	
	private List<ProductRentalPricesDTO> rentprices;
	
	
	public static ProductSlimResponseEntity fromProductDTO(ProductDTO dto ) {
		return ProductSlimResponseEntity.builder()
				.productID(dto.getProductID())
				.productName(dto.getProductName())
				.categoryDTO(dto.getCategory())
				.productCondition(dto.getProductCondition())
				.productAvt(dto.getProductAvt())
				.price(dto.getPrice())
				.checkType(dto.getCheckType().toString())
				.status(dto.getStatus().toString())
				.productOwnerName(dto.getProductownerDTO().getFullName())
				.rentprices(dto.getRentprices())
				.productSpecifications(dto.getProductSpecificationData())
				.build();
	}
}
