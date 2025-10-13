package com.example.case_study_module_4.service;

import com.example.case_study_module_4.dto.CreateOrderDto;
import com.example.case_study_module_4.entity.Order;
import jakarta.transaction.Transactional;

import java.util.List;

public interface IOrderService {
    List<Order> getOrdersByUser(Long userId);

    @Transactional
    Order createOrder(CreateOrderDto dto);

    Order getOrderById(Long orderId);

    boolean markCompleted(Long orderId);

    List<Order> findByRestaurantId(Long restaurantId);

}

