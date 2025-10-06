package com.example.case_study_module_4.dto;

import lombok.Data;
import java.util.List;

@Data
public class CreateOrderDto {
    private Long userId;
    private Long restaurantId;
    private List<CartItemDto> items;
}
