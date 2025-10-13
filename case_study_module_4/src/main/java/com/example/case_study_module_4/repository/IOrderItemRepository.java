package com.example.case_study_module_4.repository;

import com.example.case_study_module_4.entity.OrderItem;
import com.example.case_study_module_4.entity.OrderItemKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderItemRepository extends JpaRepository<OrderItem, OrderItemKey> {

    // ✅ Lấy danh sách món ăn bán chạy nhất theo tổng số lượng
    @Query("SELECT oi.food.id, SUM(oi.quantity) AS totalSold " +
            "FROM OrderItem oi " +
            "GROUP BY oi.food.id " +
            "ORDER BY totalSold DESC")
    List<Object[]> findTopFoodsBySales();
}
