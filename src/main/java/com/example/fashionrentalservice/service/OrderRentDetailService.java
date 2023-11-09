package com.example.fashionrentalservice.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fashionrentalservice.model.dto.order.OrderRentDetailDTO;
import com.example.fashionrentalservice.repositories.OrderRentDetailRepository;
import com.example.fashionrentalservice.repositories.OrderRentRepository;
import com.example.fashionrentalservice.repositories.ProductRepository;

@Service
public class OrderRentDetailService {

	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private OrderRentRepository rentRepo;
	
	@Autowired
	private OrderRentDetailRepository rentDetailRepo;




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
//	public List<OrderBuyDetailResponseEntity> getAllOrderDetailByOrderBuyID(int orderBuyID) {
//		return OrderBuyDetailResponseEntity.fromListOrderBuyDetailDTO(buyDetailRepo.findAllOrderDetailByOrderBuyID(orderBuyID));
//	}
	
	
	//================================== Lay tat ca OrderRentDetail By ProductID========================================
	public List<OrderRentDetailDTO> getOrderRentDetailByProductIDAndCheckDate(int productID,LocalDate date) {
		List<OrderRentDetailDTO> detail = rentDetailRepo.findAllOrderDetailByProductIDAndCheckDate(productID, date);
		return detail;
	}
	
}
	
	