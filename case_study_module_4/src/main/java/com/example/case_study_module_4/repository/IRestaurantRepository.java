package com.example.case_study_module_4.repository;

import com.example.case_study_module_4.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface IRestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findTop8ByOrderByIdDesc();
    @Query("SELECT r FROM Restaurant r WHERE LOWER(r.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Restaurant> searchByTitle(String keyword);

    Restaurant findByAccountId(Long accountId);
    @Query("SELECT COALESCE(SUM(o.totalPrice), 0) FROM Order o")
    BigDecimal getTotalRevenue();

//    @Query("SELECT COUNT(r) FROM Restaurant r WHERE r.active = true")
//    Long countActiveRestaurants();
}
