package com.example.fashionrentalservice.model.response;

import java.util.List;
import java.util.stream.Collectors;

import com.example.fashionrentalservice.model.dto.product.ProductImgDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductImgResponseEntity {
	private int productImgID;
	
	private String imgUrl;
	
	private int productID;
	
	public static List<ProductImgResponseEntity> fromListProductImgDTO(List<ProductImgDTO> dtos) {
		return dtos.stream()
	            .map(dto -> ProductImgResponseEntity.builder()
	                    .productImgID(dto.getProductImgID())
	                    .imgUrl(dto.getImgUrl())
	                    .productID(dto.getProductDTO().getProductID())
	                    .build())
	            .collect(Collectors.toList());
	}
	
	public static ProductImgResponseEntity fromProductImgDTO(ProductImgDTO dto) {
		return ProductImgResponseEntity.builder()
				.productImgID(dto.getProductImgID())
				.imgUrl(dto.getImgUrl())
				.productID(dto.getProductDTO().getProductID())
				.build();
	}
}
