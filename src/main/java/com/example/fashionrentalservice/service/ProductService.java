package com.example.fashionrentalservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fashionrentalservice.exception.StaffNotFoundByID;
import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.dto.product.ProductDTO;
import com.example.fashionrentalservice.model.request.ProductRequestEntity;
import com.example.fashionrentalservice.model.response.ProductResponseEntity;
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
//    public ProductResponseEntity updateProductByID(int productID,ProductRequestEntity entity) {
//    	ProductDTO dto = productRepo.findById(productID).orElseThrow();
//    	dto.setProductName(entity.getProductName());
//    	dto.setDescription(entity.getDescription());
//    	dto.setPrice(entity.getPrice());
//    	dto.setAddress(entity.getAddress());
//    	
//    	return POResponseEntity.fromPODTO(poRepo.save(dto));
//    }
	//================================== Get All Product========================================
//	public ProductResponseEntity getAllProduct(int productID) throws CrudException{
//
//		List<ProductDTO> dto = productRepo.findAll();
//		if(dto==null) 
//			throw new StaffNotFoundByID();
//		
//		return ProductResponseEntity.fromProductDTO(dto);
//	}
	//================================== Delete Product========================================
}
