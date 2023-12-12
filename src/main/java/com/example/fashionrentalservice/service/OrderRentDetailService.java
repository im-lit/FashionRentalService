package com.example.fashionrentalservice.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fashionrentalservice.exception.PendingMoneyNegative;
import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.dto.order.OrderRentDetailDTO;
import com.example.fashionrentalservice.model.dto.product.ProductDTO;
import com.example.fashionrentalservice.model.response.OrderRentDetailResponseEntity;
import com.example.fashionrentalservice.model.response.OrderRentDetailStartDateEndDateResponseEntity;
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
	public List<OrderRentDetailResponseEntity> getAllOrderDetailByOrderRentID(int orderRentID) {
		List<OrderRentDetailDTO> list = new ArrayList<>();
		List<OrderRentDetailResponseEntity> listReturn = new ArrayList<>();
		
		list = rentDetailRepo.findAllOrderDetailByOrderRentID(orderRentID);
		
		for (OrderRentDetailDTO x : list) {
			long daysRemaining = ChronoUnit.DAYS.between(LocalDate.now(), x.getEndDate());
			OrderRentDetailResponseEntity dto = OrderRentDetailResponseEntity.builder()
												.cocMoney(x.getCocMoney())
												.startDate(x.getStartDate())
												.endDate(x.getEndDate())
												.orderRentDetailID(x.getOrderRentDetailID())
												.productDTO(x.getProductDTO())
												.rentPrice(x.getRentPrice())
												.orderRentID(x.getOrderRentDTO().getOrderRentID())
												.dayRemaining(daysRemaining)
												.build();
			listReturn.add(dto);	
		}
		return listReturn;
	}
	
	
	public List<OrderRentDetailDTO> getAllOrderDetailByOrderRentIDReturnDTO(int orderRentID) {
		List<OrderRentDetailDTO> list = new ArrayList<>();	
		list = rentDetailRepo.findAllOrderDetailByOrderRentID(orderRentID);
		return list;
	}
	
	public long getRemainingDay(LocalDate endDate) {
		long daysRemaining = ChronoUnit.DAYS.between(LocalDate.now(), endDate);
		return daysRemaining;
	}
	//================================== Lay tat ca OrderRentDetail By ProductID========================================
	public OrderRentDetailResponseEntity getOrderRentDetailByProductID(int productID) throws CrudException {
		OrderRentDetailDTO dto = rentDetailRepo.findOrderDetailByProductID(productID);
		if(dto == null)
			throw new PendingMoneyNegative("Not found orderRentdetail");
		return OrderRentDetailResponseEntity.fromOrderRentDetailDTO(dto);
	}
	
	public OrderRentDetailStartDateEndDateResponseEntity getOrderRentDetailByProductIDAndProductStatusRenting(int productID) throws CrudException {
		ProductDTO check = productRepo.findById(productID).orElse(null);
		if(check == null)
			throw new PendingMoneyNegative("Not Found Product!");
		OrderRentDetailDTO dto = rentDetailRepo.findOrderDetailByProductIDAndProductStatusRenting(productID);
		if(dto == null)
			throw new PendingMoneyNegative("Not found orderRentdetail");
		return OrderRentDetailStartDateEndDateResponseEntity.fromOrderRentDetailDTO(dto);
	}
	
	
	//================================== Lay tat ca OrderRentDetail By ProductID And OrderRentID========================================
	public OrderRentDetailDTO getOrderRentDetailByProductIDAndCheckDate(int productID,LocalDate date) {
		OrderRentDetailDTO detail = rentDetailRepo.findAllOrderDetailByProductIDAndCheckDate(productID, date);
		return detail;
	}
	
	public OrderRentDetailResponseEntity getOrderRentDetailByProductIDAndOrderRentID(int productID, int orderRentID) throws CrudException {
		OrderRentDetailDTO dto = rentDetailRepo.findOrderDetailByProductIDAndOrderRentID(productID,orderRentID);
		if(dto == null)
			throw new PendingMoneyNegative("Not found orderRentdetail");
		return OrderRentDetailResponseEntity.fromOrderRentDetailDTO(dto);
	}
	
	
	public List<OrderRentDetailDTO> getAllOrderRentDetail() {
		List<OrderRentDetailDTO> list = rentDetailRepo.findAll();
		return list;
	}
	
}
	
	
