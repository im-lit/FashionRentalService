package com.example.fashionrentalservice.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fashionrentalservice.exception.CreateOrderFailed;
import com.example.fashionrentalservice.exception.CusNotFoundByID;
import com.example.fashionrentalservice.exception.PONotFoundByID;
import com.example.fashionrentalservice.exception.ProductNotFoundByID;
import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.dto.account.CustomerDTO;
import com.example.fashionrentalservice.model.dto.account.ProductOwnerDTO;
import com.example.fashionrentalservice.model.dto.order.OrderBuyDTO;
import com.example.fashionrentalservice.model.dto.order.OrderBuyDTO.OrderBuyStatus;
import com.example.fashionrentalservice.model.dto.order.OrderBuyDetailDTO;
import com.example.fashionrentalservice.model.dto.product.ProductDTO;
import com.example.fashionrentalservice.model.request.OrderBuyDetailRequestEntity;
import com.example.fashionrentalservice.model.request.OrderBuyRequestEntity;
import com.example.fashionrentalservice.model.response.OrderBuyResponseEntity;
import com.example.fashionrentalservice.repositories.CustomerRepository;
import com.example.fashionrentalservice.repositories.OrderBuyDetailRepository;
import com.example.fashionrentalservice.repositories.OrderBuyRepository;
import com.example.fashionrentalservice.repositories.ProductOwnerRepository;
import com.example.fashionrentalservice.repositories.ProductRepository;

@Service
public class OrderBuyService {

	@Autowired
	private CustomerRepository cusRepo;
	
	@Autowired
	private ProductOwnerRepository poRepo;
	
	@Autowired
	private OrderBuyRepository buyRepo;
	
	@Autowired
	private OrderBuyDetailRepository buyDetailRepo;
	
	@Autowired
	private ProductRepository productRepo;




//================================== Tạo mới OrderBuy ========================================
    public List<OrderBuyResponseEntity> createOrderBuy(List<OrderBuyRequestEntity> entity) throws CrudException{
        List<OrderBuyDTO> listOrder = new ArrayList<>();
        List<OrderBuyDetailDTO> listOrderDetail = new ArrayList<>();
        
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
        							.customerAddress(x.getCustomerAddress())
        							.customerDTO(cus)
        							.productownerDTO(po)
        							.build();        	
        	for (OrderBuyDetailRequestEntity detail : x.getOrderDetail()) {
        		ProductDTO product = productRepo.findById(detail.getProductID()).orElse(null);
        		if(product == null)
        			throw new ProductNotFoundByID();
        		OrderBuyDetailDTO detailBuy = OrderBuyDetailDTO.builder()
        										.productDTO(product)
        										.orderBuyDTO(orderBuy)
        										.price(detail.getPrice())
        										.build();
        		listOrderDetail.add(detailBuy);
			}
        	listOrder.add(orderBuy);
        }
        buyRepo.saveAll(listOrder);
        buyDetailRepo.saveAll(listOrderDetail);
        try {
            return OrderBuyResponseEntity.fromListOrderBuyDTO(listOrder);
        } catch (Exception e) {
        	e.printStackTrace();
            throw new CreateOrderFailed();
        } 
    }


//================================== Lay tat ca Order by CustomerID========================================
	public List<OrderBuyResponseEntity> getAllOrderByCustomerID(int customerID) {
		return OrderBuyResponseEntity.fromListOrderBuyDTO(buyRepo.findAllOrderBuyByCustomerID(customerID));
	}
	
	//================================== Lay tat ca Order by ProductOwnerID========================================
	public List<OrderBuyResponseEntity> getAllOrderByProductOwnerID(int productownerID) {
		return OrderBuyResponseEntity.fromListOrderBuyDTO(buyRepo.findAllOrderBuyByProductOwnerID(productownerID));
	}
	
	//================================== Lay tat ca Order ========================================
	public List<OrderBuyResponseEntity> getAllOrder() {
		return OrderBuyResponseEntity.fromListOrderBuyDTO(buyRepo.findAll());
	}
	
	//================================== Lay tat ca Order trong 1 thang by ProductOwnerID ========================================
	public List<OrderBuyResponseEntity> getTotal1MonthOrderByProductOwnerID(int productOwnerID) {
		LocalDate startDate = LocalDate.now().minusMonths(1);
		List<OrderBuyDTO> list = buyRepo.getTotal1MonthByProductOwnerID(productOwnerID, startDate);
		return  OrderBuyResponseEntity.fromListOrderBuyDTO(list);
	}
	
	public List<OrderBuyResponseEntity> getTotal1WeekOrderByProductOwnerID(int productOwnerID) {
		LocalDate startDate = LocalDate.now().minusWeeks(7);
		List<OrderBuyDTO> list = buyRepo.getTotal1WeekByProductOwnerID(productOwnerID, startDate);
		return  OrderBuyResponseEntity.fromListOrderBuyDTO(list);
	}
	
}
	
	
