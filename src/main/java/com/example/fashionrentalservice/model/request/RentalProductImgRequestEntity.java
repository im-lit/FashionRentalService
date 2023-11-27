package com.example.fashionrentalservice.model.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalProductImgRequestEntity {

	private List<String> img;

	private int accountID;
	
	private String status;

	private int orderRentID;

}