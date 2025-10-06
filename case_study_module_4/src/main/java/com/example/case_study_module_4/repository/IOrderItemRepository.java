package com.example.case_study_module_4.repository;

import com.example.case_study_module_4.entity.OrderItem;
import com.example.case_study_module_4.entity.OrderItemKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderItemRepository extends JpaRepository<OrderItem, OrderItemKey> {
}
