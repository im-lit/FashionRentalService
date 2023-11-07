package com.example.fashionrentalservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.fashionrentalservice.model.dto.product.ProductDTO;
import com.example.fashionrentalservice.model.dto.product.StaffRequestedDTO;

public interface ProductRepository extends JpaRepository<ProductDTO, Integer>{
	@Query("select dto from ProductDTO dto where dto.productownerDTO.productownerID = ?1")
	List<ProductDTO>findAllByProductOwnerID(int productownerID);
	
//	@Query("select dto from StaffRequestedDTO dto where dto.staffDTO.staffID = ?1 AND dto.requestAddingProductDTO.status = 'APPROVED' ORDER BY dto.staffRequestedID DESC")
//	List<StaffRequestedDTO> findAllApprovedRequestedByStaffID(int staffID);
	@Query("SELECT dto FROM ProductDTO dto WHERE dto.checkType ='SALE' ")
	List<ProductDTO> findAllSaleProduct();
//	
	@Query("SELECT dto FROM ProductDTO dto WHERE dto.checkType ='RENT' ")
	List<ProductDTO> findAllRentProduct();
	
	@Query("SELECT dto FROM ProductDTO dto WHERE dto.category.categoryName =?1 ")
	List<ProductDTO> findAllByCategory(String categoryname);

	
    @Query("SELECT dto FROM ProductDTO dto WHERE dto.status NOT IN ('BLOCKED', 'WAITING')")
    List<ProductDTO> findAllActiveProducts();
}
