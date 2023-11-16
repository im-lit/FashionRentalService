package com.example.fashionrentalservice.service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fashionrentalservice.exception.PendingMoneyNegative;
import com.example.fashionrentalservice.exception.ProductIsSoldOut;
import com.example.fashionrentalservice.exception.ProductStatusNotApproved;
import com.example.fashionrentalservice.exception.StaffNotFoundByID;
import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.dto.account.AccountDTO.AccountStatus;
import com.example.fashionrentalservice.model.dto.account.ProductOwnerDTO;
import com.example.fashionrentalservice.model.dto.product.ProductDTO;
import com.example.fashionrentalservice.model.dto.product.ProductDTO.ProductStatus;
import com.example.fashionrentalservice.model.dto.product.RequestAddingProductDTO.AddProductStatus;
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
    	ProductOwnerDTO po = poRepo.findById(entity.getProductownerID()).orElse(null);
    	if(po.getAccountDTO().getStatus() == AccountStatus.NOT_VERIFIED) 
    		throw new PendingMoneyNegative("Your account is not verified");
    	
    	ProductDTO dto = ProductDTO.builder()
                .productName(entity.getProductName())
                .productReceiptUrl(entity.getProductReceiptUrl())
                .productAvt(entity.getProductAvt())
                .productCondition(entity.getProductCondition())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .status(ProductDTO.ProductStatus.valueOf(entity.getStatus()))
                .checkType(ProductDTO.checkTypeSaleorRentorSaleRent.valueOf(entity.getCheckType()))
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
    public ProductResponseEntity updateStatusProductByID(int productID) throws CrudException {
    	ProductDTO dto = productRepo.findById(productID).orElseThrow();


    	 if(dto.getRequestAddingProductDTO().getStatus().equals(AddProductStatus.APPROVED)){
     		if(dto.getStatus().equals(ProductStatus.AVAILABLE)) {
     			dto.setStatus(ProductStatus.BLOCKED);
     		}else if(dto.getStatus().equals(ProductStatus.BLOCKED)) {
     			dto.setStatus(ProductStatus.AVAILABLE);
     		}   	
    	 }
    	 else if(dto.getRequestAddingProductDTO().getStatus().equals(AddProductStatus.APPROVING)){
    		 if(dto.getStatus().equals(ProductStatus.WAITING)) {
      			dto.setStatus(ProductStatus.BLOCKED);
      		}else if(dto.getStatus().equals(ProductStatus.BLOCKED)) {
      			dto.setStatus(ProductStatus.WAITING);
      		}
    		
    	 } else if(dto.getRequestAddingProductDTO().getStatus().equals(AddProductStatus.NOT_APPROVED)){
    		 throw new ProductStatusNotApproved();
    	 }
    	 if(dto.getStatus().equals(ProductStatus.SOLD_OUT)){
    		 throw new ProductIsSoldOut("Product Is SoldOut!");
    	 }
    	 if(dto.getStatus().equals(ProductStatus.RENTING)) {
    		 throw new ProductIsSoldOut("Product is Renting you cannot updated status");
    	 }
    	 
    	
    
    	return ProductResponseEntity.fromProductDTO(productRepo.save(dto));
    }
    
    public ProductResponseEntity updateStatusProductByIDStaff(int productID,ProductStatus status) throws CrudException {
    	ProductDTO dto = productRepo.findById(productID).orElseThrow();
    		dto.setStatus(status);
    				
    	return ProductResponseEntity.fromProductDTO(productRepo.save(dto));
    }
          
    
    
//	================================== Update ProductStatus to SOLD_OUT========================================
    public List<ProductDTO> updateListProductStatus(List <ProductDTO>product) {
    	List<ProductDTO> soldOutProduct = new ArrayList<>();	
    	for (ProductDTO x : product) {
			x.setStatus(ProductStatus.SOLD_OUT);
			soldOutProduct.add(x);
		}  	
    	return productRepo.saveAll(soldOutProduct);
    }
    
//	================================== Update ProductStatus to RENTING ngoại trừ Product đã có status là RENTING========================================
    public List<ProductDTO> updateListProductStatusExceptRentingStatus(List <ProductDTO>product) {
    	List<ProductDTO> listProduct = new ArrayList<>();
    	for (ProductDTO x : product) {
    		if(x.getStatus() != ProductDTO.ProductStatus.RENTING)
			x.setStatus(ProductStatus.RENTING);
			listProduct.add(x);
		}  	
    	return productRepo.saveAll(listProduct);
    }   
	
//	================================== Get All Product========================================
	public List<ProductSlimResponseEntity> getAllProduct() throws CrudException{
		return  productRepo.findAllActiveProducts().stream()
                .map(ProductSlimResponseEntity::fromProductDTO)
                .collect(Collectors.toList());
	}
	
	public List<ProductResponseEntity> getAllProductByProductOwnerID(int productownerID) throws CrudException{
		return  productRepo.findAllByProductOwnerID(productownerID).stream()
                .map(ProductResponseEntity::fromProductDTO)
                .collect(Collectors.toList());
	}
	
	public List<ProductSlimResponseEntity> getAllProductByProductOwnerIDForCus(int productownerID) throws CrudException{
		return  productRepo.findAllAvailbleAndRentingByProductOwnerID(productownerID).stream()
                .map(ProductSlimResponseEntity::fromProductDTO)
                .collect(Collectors.toList());
	}
	public List<ProductSlimResponseEntity> getAllProductsOnSale() throws CrudException{
		return  productRepo.findAllSaleProduct().stream()
                .map(ProductSlimResponseEntity::fromProductDTO)
                .collect(Collectors.toList());
	}
	public List<ProductSlimResponseEntity> getAllProductsOnRent() throws CrudException{
		return  productRepo.findAllRentProduct().stream()
                .map(ProductSlimResponseEntity::fromProductDTO)
                .collect(Collectors.toList());
	}
	public List<ProductSlimResponseEntity> getAllProductsOnAvailable() throws CrudException{
		return  productRepo.findAllAvailableProduct().stream()
                .map(ProductSlimResponseEntity::fromProductDTO)
                .collect(Collectors.toList());
	}
	public List<ProductSlimResponseEntity> getAllProductsOnSoldOut() throws CrudException{
		return  productRepo.findAllSoldOutProduct().stream()
                .map(ProductSlimResponseEntity::fromProductDTO)
                .collect(Collectors.toList());
	}
	
	public List<ProductSlimResponseEntity> getProductbyCategory(String categoryName) throws CrudException{
		return  productRepo.findAllByCategory(categoryName).stream()
                .map(ProductSlimResponseEntity::fromProductDTO)
                .collect(Collectors.toList());
		
	}
	public List<ProductSlimResponseEntity> getProductBrandName(String brandName) throws CrudException{
		return  productRepo.findAllByBrandName(brandName).stream()
                .map(ProductSlimResponseEntity::fromProductDTO)
                .collect(Collectors.toList());
		
	}
	
	
	//================================== Delete Product========================================
	
	public  ProductResponseEntity deleteProductbyProductID(int productID) throws CrudException{
		ProductDTO check = productRepo.findById(productID).orElse(null);
		if(check == null)
			throw new PendingMoneyNegative("ProductID not found!");
		productRepo.delete(check);
		return  ProductResponseEntity.fromProductDTO(check);
		
	}
}
