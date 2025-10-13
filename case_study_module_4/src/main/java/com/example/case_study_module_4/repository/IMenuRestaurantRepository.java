package com.example.case_study_module_4.repository;

import com.example.case_study_module_4.entity.MenuRestaurant;
import com.example.case_study_module_4.entity.MenuRestaurantId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IMenuRestaurantRepository extends JpaRepository<MenuRestaurant, MenuRestaurantId> {
    List<MenuRestaurant> findAllByFoodId(Long foodId);
    @Query("SELECT mr FROM MenuRestaurant mr " +
            "JOIN FETCH mr.food " +
            "JOIN FETCH mr.restaurant")
    List<MenuRestaurant> findAllWithFoodAndRestaurant();

    @Query("SELECT mr FROM MenuRestaurant mr JOIN FETCH mr.food JOIN FETCH mr.restaurant WHERE mr.food.id = :foodId")
    Optional<MenuRestaurant> findByFood_Id(@Param("foodId") Long foodId);
}