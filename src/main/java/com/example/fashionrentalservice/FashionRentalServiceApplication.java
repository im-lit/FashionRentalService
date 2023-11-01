package com.example.fashionrentalservice;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FashionRentalServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FashionRentalServiceApplication.class, args);
		LocalDate currentDate = LocalDate.now();
        System.out.println("Time: " + currentDate);
        
        LocalDateTime currentDateTime = LocalDateTime.now();
        System.out.println("Date and Time: " + currentDateTime);
	}

}
