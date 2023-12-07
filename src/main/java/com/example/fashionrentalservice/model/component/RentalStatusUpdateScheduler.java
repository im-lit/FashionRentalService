package com.example.fashionrentalservice.model.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.fashionrentalservice.model.dto.order.OrderRentDetailDTO;
import com.example.fashionrentalservice.exception.handlers.CrudException;
import com.example.fashionrentalservice.model.dto.order.OrderRentDTO;
import com.example.fashionrentalservice.model.dto.order.OrderRentDTO.OrderRentStatus;
import com.example.fashionrentalservice.repositories.OrderRentRepository;
import com.example.fashionrentalservice.service.OrderRentDetailService;
import com.example.fashionrentalservice.service.OrderRentService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Component
public class RentalStatusUpdateScheduler {

    @Autowired
    private OrderRentDetailService rentService;
    
    @Autowired
    private OrderRentService orRentService;
    
    @Autowired
	private OrderRentRepository rentRepo;

    @Scheduled(fixedRate = 86400000) // Cập nhật mỗi ngày (24 giờ)
    public void updateRentStatus() {
        List<OrderRentDetailDTO> rentals = rentService.getAllOrderRentDetail();
        LocalDate currentDate = LocalDate.now();

        for (OrderRentDetailDTO x : rentals) {
            LocalDate endDate = x.getEndDate();
            long daysRemaining = ChronoUnit.DAYS.between(currentDate, endDate);

            if (daysRemaining < 0 && x.getOrderRentDTO().getStatus() == OrderRentStatus.RENTING) {
            }
        }
    }
    
    @Scheduled(fixedRate = 86400000) // Cập nhật mỗi ngày (24 giờ)
    public void updateRentStatusAfterCusReturning() throws CrudException {
        List<OrderRentDTO> orRent = orRentService.getAllOrderRent();
        List<OrderRentDTO> after1DaysRent = new ArrayList<>();
       // LocalDate currentDate = LocalDate.now();
        //tru nhung don co returning va rejecting
        for (OrderRentDTO x : orRent) {
        	if(x.getStatus() ==	OrderRentStatus.RETURNING || x.getStatus() == OrderRentStatus.REJECTING) {
        		int returningDays = x.getReturningDate() - 1;
        		x.setReturningDate(returningDays);
        	}   	
        if(x.getReturningDate()	==	0 && x.getStatus()	==	OrderRentStatus.RETURNING) {
    		orRentService.updateOrderRentByOrderRentID(x.getOrderRentID(), OrderRentStatus.COMPLETED);
    	}
    	if(x.getReturningDate()	==	0 && x.getStatus()	==	OrderRentStatus.REJECTING) {
    		orRentService.updateOrderRentByOrderRentID(x.getOrderRentID(), OrderRentStatus.REJECTING_COMPLETED);
    	}
    	after1DaysRent.add(x);
        }
        rentRepo.saveAll(after1DaysRent);   
    }
}
