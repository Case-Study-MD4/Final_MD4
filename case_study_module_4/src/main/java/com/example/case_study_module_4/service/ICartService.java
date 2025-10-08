package com.example.case_study_module_4.service;

import com.example.case_study_module_4.dto.CartItemDto;
import com.example.case_study_module_4.entity.Food;

import java.math.BigDecimal;
import java.util.List;

public interface ICartService {
        List<CartItemDto> getCart();
        void addToCart(Food food, int quantity);
        void remove(Long foodId);
        void clear();
        BigDecimal getTotal();
        void updateQuantity(Long foodId, int quantity);
        double getItemSubtotal(Long foodId);

}
