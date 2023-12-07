package com.example.fashionrentalservice;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FashionRentalServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FashionRentalServiceApplication.class, args);
		LocalDate lastWeek = LocalDate.now().minusWeeks(1);
        System.out.println("Time: " + lastWeek);
        
        LocalDateTime currentDateTime = LocalDateTime.now();
        System.out.println("Date and Time: " + currentDateTime);
	}

}
