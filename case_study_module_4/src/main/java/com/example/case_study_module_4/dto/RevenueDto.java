package com.example.case_study_module_4.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RevenueDto {
    private String restaurantName;
    private Long orderCount;
    private BigDecimal totalRevenue;

}
