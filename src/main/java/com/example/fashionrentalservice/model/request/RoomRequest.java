package com.example.fashionrentalservice.model.request;

import lombok.Data;

import java.util.List;

@Data
public class RoomRequest {
    String name;
    List<Integer> members;
}
