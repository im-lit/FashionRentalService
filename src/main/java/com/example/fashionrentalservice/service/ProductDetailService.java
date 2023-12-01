package com.example.fashionrentalservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fashionrentalservice.exception.PendingMoneyNegative;
import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.dto.product.CategoryDTO;
import com.example.fashionrentalservice.model.dto.product.ProductDTO;
import com.example.fashionrentalservice.model.dto.product.ProductDetailDTO;
import com.example.fashionrentalservice.model.request.ProductDetailRequestEntity;
import com.example.fashionrentalservice.repositories.ProductDetailRepository;
import com.example.fashionrentalservice.repositories.ProductRepository;

@Service
public class ProductDetailService {

	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private ProductDetailRepository detailRepo;
	


//================================== Tạo mới ProductDetail ========================================
    public List<ProductDetailDTO> createProductDetail(List<ProductDetailRequestEntity> entity) throws CrudException{
    	List<ProductDetailDTO> list = new ArrayList<>();
    	for (ProductDetailRequestEntity x : entity) {
        	ProductDTO check = productRepo.findById(x.getProductID()).orElse(null);
        	if(check == null)
        		throw new PendingMoneyNegative("Product not found!");
        	ProductDetailDTO dto = ProductDetailDTO.builder()
        			.detailName(x.getDetailName())
        			.value(x.getValue())
        			.productDTO(check)
                    .build();
        	list.add(dto);
		}
        return detailRepo.saveAll(list);
    }		
	
//================================== Xóa ProductDetail========================================
    public ProductDetailDTO deleteExistedProductDetail(int id) {
    	ProductDetailDTO dto = detailRepo.findById(id).orElseThrow();
    	detailRepo.deleteById(id);
        return dto;
    }
}
	
	
