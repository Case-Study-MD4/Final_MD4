package com.example.case_study_module_4.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CartItemDto {
    private Long foodId;
    private String foodName;
    private BigDecimal price;
    private int quantity;
}
