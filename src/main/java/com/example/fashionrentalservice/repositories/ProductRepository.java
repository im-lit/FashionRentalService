package com.example.fashionrentalservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.fashionrentalservice.model.dto.product.ProductDTO;
import com.example.fashionrentalservice.model.dto.product.StaffRequestedDTO;

public interface ProductRepository extends JpaRepository<ProductDTO, Integer>{
	@Query("select dto from ProductDTO dto where dto.productownerDTO.productownerID = ?1 ORDER BY CASE WHEN dto.status = 'AVAILABLE' THEN 1 WHEN dto.status = 'WAITING' THEN 2 WHEN dto.status = 'BLOCKED' THEN 3 WHEN dto.status = 'RENTING' THEN 4 ELSE 5 END")
	List<ProductDTO>findAllByProductOwnerID(int productownerID);
	
	@Query("select dto from ProductDTO dto where dto.productownerDTO.productownerID = ?1 AND  dto.checkType ='RENT'  AND dto.status NOT IN ('WAITING', 'BLOCKED')")
	List<ProductDTO>findAllRentTypeByProductOwnerID(int productownerID);
	

	@Query("select dto from ProductDTO dto where dto.productownerDTO.productownerID = ?1 AND  dto.checkType ='SALE'  AND dto.status NOT IN ('WAITING', 'BLOCKED')")
	List<ProductDTO>findAllSaleTypeByProductOwnerID(int productownerID);
	
	
	@Query("select dto from ProductDTO dto where dto.productownerDTO.productownerID = ?1 AND dto.status IN ('AVAILABLE', 'RENTING','SOLD_OUT') ORDER BY CASE WHEN dto.status = 'AVAILABLE' THEN 1   WHEN dto.status = 'RENTING' THEN 2 WHEN dto.status = 'SOLD_OUT' THEN 3  END")
	List<ProductDTO>findAllAvailbleAndRentingByProductOwnerID(int productownerID);
	
//	@Query("select dto from StaffRequestedDTO dto where dto.staffDTO.staffID = ?1 AND dto.requestAddingProductDTO.status = 'APPROVED' ORDER BY dto.staffRequestedID DESC")
//	List<StaffRequestedDTO> findAllApprovedRequestedByStaffID(int staffID);
	@Query("SELECT dto FROM ProductDTO dto WHERE dto.checkType ='SALE' ")
	List<ProductDTO> findAllSaleProduct();
//	@Query("SELECT dto FROM ProductDTO dto WHERE JSON_EXTRACT(dto.productSpecificationData, '$.brandNameWatch') = :brandName")
	 @Query("SELECT dto FROM ProductDTO dto WHERE " +
    "(dto.category.categoryName = 'Watch' AND JSON_EXTRACT(dto.productSpecificationData, '$.brandNameWatch') = :brandName) OR " +
    "(dto.category.categoryName = 'Shoe' AND JSON_EXTRACT(dto.productSpecificationData, '$.brandNameShoes') = :brandName) OR " +
    "(dto.category.categoryName = 'Sunglasses' AND JSON_EXTRACT(dto.productSpecificationData, '$.brandNameGlasses') = :brandName) OR " +
    "(dto.category.categoryName = 'Jewelry' AND JSON_EXTRACT(dto.productSpecificationData, '$.brandNameJewelry') = :brandName) OR " +
    "(dto.category.categoryName = 'Hat' AND JSON_EXTRACT(dto.productSpecificationData, '$.brandNameHat') = :brandName) OR " +
    "(dto.category.categoryName = 'Bag' AND JSON_EXTRACT(dto.productSpecificationData, '$.brandNameBag') = :brandName)"+
    " AND dto.status NOT IN ('WAITING', 'BLOCKED')")
    List<ProductDTO> findAllByBrandName(String brandName);
//    @Query("SELECT dto from ProductDTO JSON_EXTRACT(p.productSpecificationData, '$.brandName') where dto.productownerDTO.productownerID = ?1")
//	 @Query("SELECT DISTINCT JSON_EXTRACT(dto.productSpecificationData, '$.brandNameWatch') AS brandName " +
//		        "FROM ProductDTO dto WHERE dto.category.categoryName = 'Watch' AND JSON_EXTRACT(dto.productSpecificationData, '$.brandNameWatch') IS NOT NULL " +
//		        "UNION " +
//		        "SELECT DISTINCT JSON_EXTRACT(dto.productSpecificationData, '$.brandNameShoes') AS brandName " +
//		        "FROM ProductDTO dto WHERE dto.category.categoryName = 'Shoe' AND JSON_EXTRACT(dto.productSpecificationData, '$.brandNameShoes') IS NOT NULL " +
//		        "UNION " +
//		        "SELECT DISTINCT JSON_EXTRACT(dto.productSpecificationData, '$.brandNameGlasses') AS brandName " +
//		        "FROM ProductDTO dto WHERE dto.category.categoryName = 'Sunglasses' AND JSON_EXTRACT(dto.productSpecificationData, '$.brandNameGlasses') IS NOT NULL " +
//		        "UNION " +
//		        "SELECT DISTINCT JSON_EXTRACT(dto.productSpecificationData, '$.brandNameJewelry') AS brandName " +
//		        "FROM ProductDTO dto WHERE dto.category.categoryName = 'Jewelry' AND JSON_EXTRACT(dto.productSpecificationData, '$.brandNameJewelry') IS NOT NULL " +
//		        "UNION " +
//		        "SELECT DISTINCT JSON_EXTRACT(dto.productSpecificationData, '$.brandNameHat') AS brandName " +
//		        "FROM ProductDTO dto WHERE dto.category.categoryName = 'Hat' AND JSON_EXTRACT(dto.productSpecificationData, '$.brandNameHat') IS NOT NULL " +
//		        "UNION " +
//		        "SELECT DISTINCT JSON_EXTRACT(dto.productSpecificationData, '$.brandNameBag') AS brandName " +
//		        "FROM ProductDTO dto WHERE dto.category.categoryName = 'Bag' AND JSON_EXTRACT(dto.productSpecificationData, '$.brandNameBag') IS NOT NULL")
////		List<String> findAllBrandNamesInCategory();
//
//	 @Query("SELECT DISTINCT " +
//		        "   WHEN dto.category.categoryName = 'Watch' THEN JSON_EXTRACT(dto.productSpecificationData, '$.brandNameWatch')" +
////		        "   WHEN dto.category.categoryName = 'Shoe' THEN JSON_EXTRACT(dto.productSpecificationData, '$.brandNameShoes') " +
////		        "   WHEN dto.category.categoryName = 'Sunglasses' THEN JSON_EXTRACT(dto.productSpecificationData, '$.brandNameGlasses') " +
////		        "   WHEN dto.category.categoryName = 'Jewelry' THEN JSON_EXTRACT(dto.productSpecificationData, '$.brandNameJewelry') " +
////		        "   WHEN dto.category.categoryName = 'Hat' THEN JSON_EXTRACT(dto.productSpecificationData, '$.brandNameHat') " +
////	        "   WHEN dto.category.categoryName = 'Bag' THEN JSON_EXTRACT(dto.productSpecificationData, '$.brandNameBag') " +
//		        "END AS brandName " +	
//		        "FROM ProductDTO dto " +
//		        "WHERE dto.category.categoryName = :categoryName " +
//		        "AND brandName IS NOT NULL")
//	List<String> findAllBrandNames(String categoryName);
//	 @Query("SELECT DISTINCT COALESCE(" +
//		        "   JSON_EXTRACT(dto.productSpecificationData, '$.brandNameWatch'), " +
//		        "   JSON_EXTRACT(dto.productSpecificationData, '$.brandNameShoes'), " +
//		        "   JSON_EXTRACT(dto.productSpecificationData, '$.brandNameGlasses'), " +
//		        "   JSON_EXTRACT(dto.productSpecificationData, '$.brandNameJewelry'), " +
//		        "   JSON_EXTRACT(dto.productSpecificationData, '$.brandNameHat'), " +
//		        "   JSON_EXTRACT(dto.productSpecificationData, '$.brandNameBag')" +
//		        ") AS brandName " +
//		        "FROM ProductDTO dto " +
//		        "WHERE dto.category.categoryName = :categoryName " +
//		        "AND COALESCE(" +
//		        "   JSON_EXTRACT(dto.productSpecificationData, '$.brandNameWatch'), " +
//		        "   JSON_EXTRACT(dto.productSpecificationData, '$.brandNameShoes'), " +
//		        "   JSON_EXTRACT(dto.productSpecificationData, '$.brandNameGlasses'), " +
//		        "   JSON_EXTRACT(dto.productSpecificationData, '$.brandNameJewelry'), " +
//		        "   JSON_EXTRACT(dto.productSpecificationData, '$.brandNameHat'), " +
//		        "   JSON_EXTRACT(dto.productSpecificationData, '$.brandNameBag')" +
//		        ") IS NOT NULL")
//		List<String> findAllBrandNames(String category);
	@Query("SELECT dto FROM ProductDTO dto WHERE dto.checkType ='RENT' AND dto.status NOT IN ('WAITING', 'BLOCKED') ")
	List<ProductDTO> findAllRentProduct();
	
	@Query("SELECT dto FROM ProductDTO dto WHERE dto.status ='AVAILABLE' AND dto.status NOT IN ('WAITING', 'BLOCKED') ")
	List<ProductDTO> findAllAvailableProduct();
	
	@Query("SELECT dto FROM ProductDTO dto WHERE dto.status ='SOLD_OUT' AND dto.status NOT IN ('WAITING', 'BLOCKED')")
	List<ProductDTO> findAllSoldOutProduct();
	
	@Query("SELECT dto FROM ProductDTO dto WHERE dto.category.categoryName =?1 AND dto.status NOT IN ('WAITING', 'BLOCKED')")
	List<ProductDTO> findAllByCategory(String categoryname);

	
	@Query("SELECT dto FROM ProductDTO dto WHERE LOWER(dto.productName) LIKE %?1% AND dto.status NOT IN ('WAITING', 'BLOCKED')")
	List<ProductDTO> findAllByProductNameLike(String productName);
	
    @Query("SELECT dto FROM ProductDTO dto WHERE dto.status NOT IN ('BLOCKED', 'WAITING')")
    List<ProductDTO> findAllActiveProducts();
}
