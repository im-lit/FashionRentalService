package com.example.fashionrentalservice.model.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.fashionrentalservice.model.dto.order.OrderRentDetailDTO;
import com.example.fashionrentalservice.model.dto.order.OrderRentDTO.OrderRentStatus;
import com.example.fashionrentalservice.service.OrderRentDetailService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class RentalStatusUpdateScheduler {

    @Autowired
    private OrderRentDetailService rentService;

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
}
