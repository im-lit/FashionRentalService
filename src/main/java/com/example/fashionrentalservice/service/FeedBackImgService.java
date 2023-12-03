package com.example.fashionrentalservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fashionrentalservice.exception.StaffNotFoundByID;
import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.dto.product.FeedBackImgDTO;
import com.example.fashionrentalservice.model.dto.product.ProductImgDTO;
import com.example.fashionrentalservice.model.request.FeedBackImgRequestEntity;
import com.example.fashionrentalservice.model.request.ProductImgRequestEntity;
import com.example.fashionrentalservice.model.response.FeedBackImgResponseEntity;
import com.example.fashionrentalservice.model.response.ProductImgResponseEntity;
import com.example.fashionrentalservice.repositories.FeedBackImgRepository;
import com.example.fashionrentalservice.repositories.FeedBackRepository;
import com.example.fashionrentalservice.repositories.ProductImgRepository;
import com.example.fashionrentalservice.repositories.ProductRepository;
@Service
public class FeedBackImgService {
	@Autowired
	private FeedBackRepository fbRepo;	
	
	@Autowired
	private FeedBackImgRepository imgRepo;
	
	public List<FeedBackImgResponseEntity> getAllProductImgbyProductID(int feedBackID) throws CrudException{
		return  imgRepo.findAllImgByFeedBackID(feedBackID).stream()
                .map(FeedBackImgResponseEntity::fromFeedBackImgDTO)
                .collect(Collectors.toList());
	}
	
	  public List<FeedBackImgResponseEntity> CreateFeedBackImg(FeedBackImgRequestEntity entity) throws CrudException{    	
	        List<FeedBackImgDTO> list = new ArrayList<>();
	        for (String path : entity.getImgUrl()) {
	        	FeedBackImgDTO imgDTO = new FeedBackImgDTO();
	        	imgDTO.setImgUrl(path);
	        	imgDTO.setFeedBackDTO(fbRepo.findById(entity.getFeedBackID()).orElseThrow());
	        	list.add(imgDTO);
	        }	           
	    	return FeedBackImgResponseEntity.fromListFeedBackImgDTO(imgRepo.saveAll(list));
	    }
	  
	    public FeedBackImgResponseEntity deleteFeedBackImg(int feedBackImgID) throws CrudException {
	    	FeedBackImgDTO dto = imgRepo.findById(feedBackImgID).orElse(null);
	    	if(dto == null)
	    		throw new StaffNotFoundByID();
	    	imgRepo.deleteById(feedBackImgID);
	        return FeedBackImgResponseEntity.fromFeedBackImgDTO(dto);
	    }
}
