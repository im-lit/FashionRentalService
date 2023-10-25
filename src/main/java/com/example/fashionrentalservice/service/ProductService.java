package com.example.fashionrentalservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fashionrentalservice.exception.StaffNotFoundByID;
import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.dto.product.ProductDTO;
import com.example.fashionrentalservice.model.dto.product.ProductDTO.ProductStatus;
import com.example.fashionrentalservice.model.request.ProductRequestEntity;
import com.example.fashionrentalservice.model.response.ProductResponseEntity;
import com.example.fashionrentalservice.model.response.ProductSlimResponseEntity;
import com.example.fashionrentalservice.repositories.CategoryRepository;
import com.example.fashionrentalservice.repositories.ProductOwnerRepository;
import com.example.fashionrentalservice.repositories.ProductRepository;
@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private CategoryRepository cateRepo;
	
	@Autowired
	private ProductOwnerRepository poRepo;
	
	//================================== Tạo Product========================================
    public ProductResponseEntity createProduct(ProductRequestEntity entity) throws CrudException{
    	
    	ProductDTO dto = ProductDTO.builder()
                .productName(entity.getProductName())
                .productReceiptUrl(entity.getProductReceiptUrl())
                .productAvt(entity.getProductAvt())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .status(ProductDTO.ProductStatus.valueOf(entity.getStatus()))
                .forRent(entity.isForRent())
                .forSale(entity.isForSale())
                .category(cateRepo.findById(entity.getCategoryID()).orElseThrow())
                .productownerDTO(poRepo.findById(entity.getProductownerID()).orElseThrow())
                .productSpecificationData(entity.getProductSpecificationData())
                .build();
        
        return ProductResponseEntity.fromProductDTO(productRepo.save(dto));
     }
    
    //================================== Tìm Product========================================   	
	public ProductResponseEntity getProductbyID(int productID) throws CrudException{

		ProductDTO dto = productRepo.findById(productID).orElse(null);
		if(dto==null) 
			throw new StaffNotFoundByID();
		
		return ProductResponseEntity.fromProductDTO(dto);
	}
	//================================== Update Product========================================
    public ProductResponseEntity updateStatusProductByID(int productID,ProductStatus status) {
    	ProductDTO dto = productRepo.findById(productID).orElseThrow();
    	dto.setStatus(status);    	
    	return ProductResponseEntity.fromProductDTO(productRepo.save(dto));
    }
	
//	================================== Get All Product========================================
	public List<ProductSlimResponseEntity> getAllProduct() throws CrudException{
		return  productRepo.findAll().stream()
                .map(ProductSlimResponseEntity::fromProductDTO)
                .collect(Collectors.toList());
	}
	
	public List<ProductSlimResponseEntity> getAllProductByProductOwnerID(int productownerID) throws CrudException{
		return  productRepo.findAllByProductOwnerID(productownerID).stream()
                .map(ProductSlimResponseEntity::fromProductDTO)
                .collect(Collectors.toList());
	}
	//================================== Delete Product========================================
}
