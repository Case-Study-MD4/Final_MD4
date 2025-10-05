package com.example.case_study_module_4.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class OrderItemKey implements Serializable {
    private Long orderId;
    private Long foodId;
}

