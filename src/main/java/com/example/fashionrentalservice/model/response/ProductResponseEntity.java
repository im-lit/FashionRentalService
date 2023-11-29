package com.example.fashionrentalservice.model.response;

import java.util.List;

import com.example.fashionrentalservice.model.dto.product.CategoryDTO;
import com.example.fashionrentalservice.model.dto.product.ProductDTO;
import com.example.fashionrentalservice.model.dto.product.ProductRentalPricesDTO;
import com.example.fashionrentalservice.model.dto.product.RequestAddingProductDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseEntity {
	private int productID;

	private String productName;
	
	private String productReceiptUrl;
	
	private String productAvt;
	
	private String productCondition;

	private String description;

	private double price;

	private String status;
	
	private String checkType;

	private CategoryDTO category;
	
	private int productOwnerID;
		
	private String productSpecificationData;
	
	private List<ProductRentalPricesDTO> rentprices;
	
	private RequestAddingProductDTO requestDTO;
	
	

	public static ProductResponseEntity fromProductDTO(ProductDTO dto) {
		return ProductResponseEntity.builder()
				.productID(dto.getProductID())
				.productName(dto.getProductName())
				.productCondition(dto.getProductCondition())
				.productAvt(dto.getProductAvt())
				.productReceiptUrl(dto.getProductReceiptUrl())
				.description(dto.getDescription())
				.price(dto.getPrice())
				.status(dto.getStatus().toString())
				.checkType(dto.getCheckType().toString())
				.category(dto.getCategory())
				.productOwnerID(dto.getProductownerDTO().getProductownerID())
				.requestDTO(dto.getRequestAddingProductDTO())
				.rentprices(dto.getRentprices())
				.productSpecificationData(dto.getProductSpecificationData())
				.build();
	}
}
