package com.example.fashionrentalservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.example.fashionrentalservice.model.dto.order.OrderBuyDetailDTO;
import com.example.fashionrentalservice.model.dto.product.FavoriteProductDTO;
import com.example.fashionrentalservice.model.dto.product.FeedBackDTO;

@EnableJpaRepositories
public interface FeedBackRepository  extends JpaRepository<FeedBackDTO, Integer>{
	@Query("SELECT dto FROM FeedBackDTO dto WHERE dto.productDTO.productID =?1")
	List<FeedBackDTO> findFeedBackByProductID(int productID);
	
//	@Query("SELECT dto FROM FavoriteProductDTO dto WHERE dto.customerDTO.customerID = ?1 AND dto.productDTO.productID =?2")
//	FavoriteProductDTO findByCustomerAndProduct(int customerID, int productID);
	
	
	@Query("SELECT dto FROM FeedBackDTO dto WHERE dto.customerDTO.customerID = ?1 AND dto.productDTO.productID =?2")
	FeedBackDTO findFeedBackByCustomerAndProductID(int customerID,int productID);
	

}
