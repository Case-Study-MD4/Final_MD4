package com.example.case_study_module_4.service.impl;

import com.example.case_study_module_4.dto.RevenueDto;

import com.example.case_study_module_4.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RevenueService {

    private final IOrderRepository orderRepository;

    public List<RevenueDto> getRevenueStatistics() {
        return orderRepository.getRevenueStatistics();
    }

    public BigDecimal getTotalRevenue(List<RevenueDto> revenues) {
        return revenues.stream()
                .map(RevenueDto::getTotalRevenue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public int getRestaurantCount(List<RevenueDto> revenues) {
        return revenues.size();
    }
}
