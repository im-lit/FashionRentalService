package com.example.fashionrentalservice.model.response;

import java.util.List;
import java.util.stream.Collectors;

import com.example.fashionrentalservice.model.dto.product.FeedBackImgDTO;
import com.example.fashionrentalservice.model.dto.product.ProductImgDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedBackImgResponseEntity {
	private int feedBackImgID;
	
	private String imgUrl;
	
	private int feedBackID;
	
	public static FeedBackImgResponseEntity fromFeedBackImgDTO(FeedBackImgDTO dto) {
		return FeedBackImgResponseEntity.builder()
				.feedBackImgID(dto.getFeedBackImgID())
				.imgUrl(dto.getImgUrl())
				.feedBackID(dto.getFeedBackDTO().getFeedbackID())
				.build();
	}
	public static List<FeedBackImgResponseEntity> fromListFeedBackImgDTO(List<FeedBackImgDTO> dtos) {
		return dtos.stream()
	            .map(dto -> FeedBackImgResponseEntity.builder()
	            		.feedBackImgID(dto.getFeedBackImgID())
	    				.imgUrl(dto.getImgUrl())
	    				.feedBackID(dto.getFeedBackDTO().getFeedbackID())
	    				.build())
	            .collect(Collectors.toList());
	}
	
}
