package com.example.fashionrentalservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.fashionrentalservice.model.dto.product.FeedBackImgDTO;
import com.example.fashionrentalservice.model.dto.product.ProductImgDTO;

@Repository
public interface FeedBackImgRepository extends JpaRepository<FeedBackImgDTO, Integer> {
	@Query("select dto from FeedBackImgDTO dto where dto.feedBackDTO.feedbackID = ?1")
	List<FeedBackImgDTO>findAllImgByFeedBackID(int feedBackID);
}
