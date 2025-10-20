package com.example.case_study_module_4.service.impl;

import com.example.case_study_module_4.dto.RevenueDto;

import com.example.case_study_module_4.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
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
////top 5 nhà hàng có doanh thu
//    public List<RevenueDto> getTop5Revenue() {
//        List<Object[]> results = orderRepository.findTop5RestaurantsByRevenue();
//
//        List<RevenueDto> dtoList = new ArrayList<>();
//        for (Object[] row : results) {
//            dtoList.add(new RevenueDto(
//                    (String) row[0],
//                    ((Number) row[1]).doubleValue()
//            ));
//        }
//        return dtoList;
//    }
}
