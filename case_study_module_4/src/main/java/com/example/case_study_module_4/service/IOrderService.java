package com.example.case_study_module_4.service;

import com.example.case_study_module_4.entity.Order;

import java.util.List;

public interface IOrderService {
    List<Order> getOrdersByUser(Long userId);
}

