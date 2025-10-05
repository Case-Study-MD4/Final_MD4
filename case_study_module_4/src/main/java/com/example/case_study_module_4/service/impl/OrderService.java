package com.example.case_study_module_4.service.impl;

import com.example.case_study_module_4.entity.Order;
import com.example.case_study_module_4.repository.IOrderRepository;
import com.example.case_study_module_4.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final IOrderRepository orderRepository;


    @Override
    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
