package com.example.fashionrentalservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.dto.product.CategoryDTO;
import com.example.fashionrentalservice.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository cateRepo;
	


//================================== Tạo mới Category ========================================
    public CategoryDTO createCategory(String name) throws CrudException{
        CategoryDTO dto = CategoryDTO.builder()
                .categoryName(name)
                .build();
        return cateRepo.save(dto);
    }

//================================== Lay tat ca Category ========================================
	public List<CategoryDTO> getAllCategory() {
		List<CategoryDTO> listCate = cateRepo.findAll();
		return listCate;
	}		
	
//================================== Xóa Category========================================
    public CategoryDTO deleteExistedCategory(int id) {
    	CategoryDTO dto = cateRepo.findById(id).orElseThrow();
    	cateRepo.deleteById(id);
        return dto;
    }
}
	
	
