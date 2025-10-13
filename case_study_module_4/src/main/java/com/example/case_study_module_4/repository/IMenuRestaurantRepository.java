package com.example.case_study_module_4.repository;

import com.example.case_study_module_4.entity.MenuRestaurant;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IMenuRestaurantRepository extends JpaRepository<MenuRestaurant, Long> {
    List<MenuRestaurant> findAllByFoodId(Long foodId);
    @Modifying
    @Transactional
    @Query("DELETE FROM MenuRestaurant mr WHERE mr.restaurant.id = :restaurantId")
    void deleteByRestaurantId(Long restaurantId);
}