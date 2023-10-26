package com.example.fashionrentalservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fashionrentalservice.exception.StaffNotFoundByID;
import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.dto.product.ProductImgDTO;
import com.example.fashionrentalservice.model.request.ProductImgRequestEntity;
import com.example.fashionrentalservice.model.response.ProductImgResponseEntity;
import com.example.fashionrentalservice.repositories.ProductImgRepository;
import com.example.fashionrentalservice.repositories.ProductRepository;
@Service
public class ProductlmgService {
	
	@Autowired
	private ProductRepository productRepo;	
	
	@Autowired
	private ProductImgRepository imgRepo;
	
	
	public List<ProductImgResponseEntity> getAllProductImgbyProductID(int productID) throws CrudException{
		return  imgRepo.findAllImgByProductID(productID).stream()
                .map(ProductImgResponseEntity::fromProductImgDTO)
                .collect(Collectors.toList());
	}
	
    public List<ProductImgResponseEntity> CreateProductImg(ProductImgRequestEntity entity) throws CrudException{    	
        List<ProductImgDTO> list = new ArrayList<>();
        for (String path : entity.getImgUrl()) {
        	ProductImgDTO productImgDTO = new ProductImgDTO();
        	productImgDTO.setImgUrl(path);
        	productImgDTO.setProductDTO(productRepo.findById(entity.getProductID()).orElseThrow());
        	list.add(productImgDTO);
        }	           
    	return ProductImgResponseEntity.fromListProductImgDTO(imgRepo.saveAll(list));
    }
    
    
    public ProductImgResponseEntity deleteProductImg(int productImgID) throws CrudException {
    	ProductImgDTO dto = imgRepo.findById(productImgID).orElse(null);
    	if(dto == null)
    		throw new StaffNotFoundByID();
    	imgRepo.deleteById(productImgID);
        return ProductImgResponseEntity.fromProductImgDTO(dto);
    }
}
