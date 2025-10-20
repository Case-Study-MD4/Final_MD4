package com.example.case_study_module_4.repository;

import com.example.case_study_module_4.dto.RevenueDto;
import com.example.case_study_module_4.entity.Order;
import com.example.case_study_module_4.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId ORDER BY o.createDate DESC")
    List<Order> findByUserId(@Param("userId") Long userId);

    Optional<Order> findByUserAndStatus(User user, int status);

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND o.status = :status")
    Optional<Order> findByUserIdAndStatus(@Param("userId") Long userId, @Param("status") int status);

    // Tìm tất cả đơn hàng của 1 nhà hàng
    @Query("SELECT o FROM Order o WHERE o.restaurant.id = :restaurantId")
    List<Order> findByRestaurantId(Long restaurantId);

    List<Order> findAllByRestaurantId(Long restaurantId);

    @Query("SELECT SUM(o.totalPrice) FROM Order o WHERE o.restaurant.id = :restaurantId")
    Double getTotalRevenueByRestaurant(@Param("restaurantId") Long restaurantId);

    @Query("""
    SELECT new com.example.case_study_module_4.dto.RevenueDto(
        r.title,
        COUNT(o.id),
        SUM(o.totalPrice)
    )
    FROM Order o
    JOIN o.restaurant r
    WHERE o.status = 2
    GROUP BY r.title
    ORDER BY SUM(o.totalPrice) DESC
""")
    List<RevenueDto> getRevenueStatistics();


    @Query("select coalesce(sum(o.totalPrice), 0) from Order o where o.status = :status")
    BigDecimal sumRevenueByStatus(@Param("status") int status);
//
//    //top 5 nhà hàng dùng limit
//    @Query(value = """
//        SELECT r.name AS restaurantName,
//               SUM(o.total_price) AS totalRevenue
//        FROM orders o
//        JOIN restaurants r ON o.restaurant_id = r.id
//        GROUP BY r.id, r.name
//        ORDER BY totalRevenue DESC
//        LIMIT 5
//        """, nativeQuery = true)
//    List<Object[]> findTop5RestaurantsByRevenue();
}

