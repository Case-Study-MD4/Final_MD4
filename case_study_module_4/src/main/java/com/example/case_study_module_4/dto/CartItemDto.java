package com.example.case_study_module_4.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    private Long foodId;
    private String foodName;
    private BigDecimal price;
    private int quantity;
    private Long restaurantId;

    public CartItemDto(Long foodId, int quantity) {
        this.foodId = foodId;
        this.quantity = quantity;
    }

    public CartItemDto(Long foodId, String foodName, Double price, int quantity, Long restaurantId) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.price = BigDecimal.valueOf(price);
        this.quantity = quantity;
        this.restaurantId = restaurantId;
    }
}
