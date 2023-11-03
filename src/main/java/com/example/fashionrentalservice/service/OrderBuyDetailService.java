package com.example.fashionrentalservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fashionrentalservice.exception.CreateOrderBuyDetailFailed;
import com.example.fashionrentalservice.exception.CreateOrderFailed;
import com.example.fashionrentalservice.exception.CusNotFoundByID;
import com.example.fashionrentalservice.exception.OrderBuyIDNotFound;
import com.example.fashionrentalservice.exception.PONotFoundByID;
import com.example.fashionrentalservice.exception.ProductNotFoundByID;
import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.dto.order.OrderBuyDTO;
import com.example.fashionrentalservice.model.dto.order.OrderBuyDetailDTO;
import com.example.fashionrentalservice.model.dto.product.ProductDTO;
import com.example.fashionrentalservice.model.request.OrderBuyDetailRequestEntity;
import com.example.fashionrentalservice.model.response.OrderBuyDetailResponseEntity;
import com.example.fashionrentalservice.model.response.OrderBuyResponseEntity;
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
    public List<OrderBuyDetailResponseEntity> createOrderBuyDetail(List<OrderBuyDetailRequestEntity> entity) throws CrudException{
        List<OrderBuyDetailDTO> list = new ArrayList<>();
        
        for (OrderBuyDetailRequestEntity x : entity) {
        	ProductDTO product = productRepo.findById(x.getProductID()).orElse(null);
        	OrderBuyDTO order = buyRepo.findById(x.getOrderBuyID()).orElse(null);
        	
        	if( product == null)
        	    throw new ProductNotFoundByID(); 	
        	if( order == null)
        	    throw new OrderBuyIDNotFound();
        	OrderBuyDetailDTO orderBuyDetail = OrderBuyDetailDTO.builder()
        							.price(x.getPrice())
        							.productDTO(product)
        							.orderBuyDTO(order)
        							.build();        	
        	list.add(orderBuyDetail);
        }	                  
        try {
            return OrderBuyDetailResponseEntity.fromListOrderBuyDetailDTO(buyDetailRepo.saveAll(list));
        } catch (Exception e) {
        	e.printStackTrace();
            throw new CreateOrderBuyDetailFailed();
        } 
    }


//================================== Lay tat ca OrderDetail By OrderBuyID========================================
	public List<OrderBuyDetailResponseEntity> getAllOrderDetailByOrderBuyID(int orderBuyID) {
		return OrderBuyDetailResponseEntity.fromListOrderBuyDetailDTO(buyDetailRepo.findAllOrderDetailByOrderBuyID(orderBuyID));
	}
	
}
	
	
