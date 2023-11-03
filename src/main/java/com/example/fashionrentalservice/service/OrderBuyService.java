package com.example.fashionrentalservice.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fashionrentalservice.exception.CreateOrderFailed;
import com.example.fashionrentalservice.exception.CusNotFoundByID;
import com.example.fashionrentalservice.exception.PONotFoundByID;
import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.dto.account.CustomerDTO;
import com.example.fashionrentalservice.model.dto.account.ProductOwnerDTO;
import com.example.fashionrentalservice.model.dto.order.OrderBuyDTO;
import com.example.fashionrentalservice.model.dto.order.OrderBuyDTO.OrderBuyStatus;
import com.example.fashionrentalservice.model.request.OrderBuyRequestEntity;
import com.example.fashionrentalservice.model.response.OrderBuyResponseEntity;
import com.example.fashionrentalservice.repositories.CustomerRepository;
import com.example.fashionrentalservice.repositories.OrderBuyRepository;
import com.example.fashionrentalservice.repositories.ProductOwnerRepository;

@Service
public class OrderBuyService {

	@Autowired
	private CustomerRepository cusRepo;
	
	@Autowired
	private ProductOwnerRepository poRepo;
	
	@Autowired
	private OrderBuyRepository buyRepo;




//================================== Tạo mới OrderBuy ========================================
    public List<OrderBuyResponseEntity> createOrderBuy(List<OrderBuyRequestEntity> entity) throws CrudException{
        List<OrderBuyDTO> list = new ArrayList<>();
        
        for (OrderBuyRequestEntity x : entity) {
        	CustomerDTO cus = cusRepo.findById(x.getCustomerID()).orElse(null);
        	ProductOwnerDTO po = poRepo.findById(x.getProductownerID()).orElse(null);
        	
        	if( cus == null)
        	    throw new CusNotFoundByID(); 	
        	if( po == null)
        	    throw new PONotFoundByID();
        	OrderBuyDTO orderBuy = OrderBuyDTO.builder()
        							.total(x.getTotal())
        							.dateOrder(LocalDate.now())
        							.status(OrderBuyStatus.PENDING)
        							.customerDTO(cus)
        							.productownerDTO(po)
        							.build();        	
        	list.add(orderBuy);
        }	                  
        try {
            return OrderBuyResponseEntity.fromListOrderBuyDTO(buyRepo.saveAll(list));
        } catch (Exception e) {
        	e.printStackTrace();
            throw new CreateOrderFailed();
        } 
    }


//================================== Lay tat ca account========================================
	public List<OrderBuyResponseEntity> getAllOrderByCustomerID(int customerID) {
		return OrderBuyResponseEntity.fromListOrderBuyDTO(buyRepo.findAllOrderBuyByCustomerID(customerID));
	}
	
	public List<OrderBuyResponseEntity> getAllOrderByProductOwnerID(int productownerID) {
		return OrderBuyResponseEntity.fromListOrderBuyDTO(buyRepo.findAllOrderBuyByProductOwnerID(productownerID));
	}
	
	public List<OrderBuyResponseEntity> getAllOrder() {
		return OrderBuyResponseEntity.fromListOrderBuyDTO(buyRepo.findAll());
	}
	
}
	
	
