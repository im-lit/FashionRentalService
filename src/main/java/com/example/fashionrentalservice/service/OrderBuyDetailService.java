package com.example.fashionrentalservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fashionrentalservice.model.dto.order.OrderBuyDetailDTO;
import com.example.fashionrentalservice.model.response.OrderBuyDetailResponseEntity;
import com.example.fashionrentalservice.repositories.OrderBuyDetailRepository;
import com.example.fashionrentalservice.repositories.OrderBuyRepository;
import com.example.fashionrentalservice.repositories.ProductRepository;

@Service
public class OrderBuyDetailService {

	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private OrderBuyRepository buyRepo;
	
	@Autowired
	private OrderBuyDetailRepository buyDetailRepo;




//================================== Tạo mới OrderBuyDetail ========================================
//    public List<OrderBuyDetailResponseEntity> createOrderBuyDetail(List<OrderBuyDetailRequestEntity> entity) throws CrudException{
//        List<OrderBuyDetailDTO> list = new ArrayList<>();
//        
//        for (OrderBuyDetailRequestEntity x : entity) {
//        	ProductDTO product = productRepo.findById(x.getProductID()).orElse(null);
//        	
//        	if( product == null)
//        	    throw new ProductNotFoundByID(); 	
//        	OrderBuyDetailDTO orderBuyDetail = OrderBuyDetailDTO.builder()
//        							.price(x.getPrice())
//        							.productDTO(product)
//        							.build();        	
//        	list.add(orderBuyDetail);
//        }	                  
//        try {
//            return OrderBuyDetailResponseEntity.fromListOrderBuyDetailDTO(buyDetailRepo.saveAll(list));
//        } catch (Exception e) {
//        	e.printStackTrace();
//            throw new CreateOrderBuyDetailFailed();
//        } 
//    }


//================================== Lay tat ca OrderDetail By OrderBuyID========================================
	public List<OrderBuyDetailResponseEntity> getAllOrderDetailByOrderBuyID(int orderBuyID) {
		return OrderBuyDetailResponseEntity.fromListOrderBuyDetailDTO(buyDetailRepo.findAllOrderDetailByOrderBuyID(orderBuyID));
	}
	
	public List<OrderBuyDetailDTO> getAllOrderDetailByOrderBuyIDReturnDTO(int orderBuyID) {
		List<OrderBuyDetailDTO> list = new ArrayList<>();
		list = buyDetailRepo.findAllOrderDetailByOrderBuyID(orderBuyID);
		return list;
	}
	
}
	
	
