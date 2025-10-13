package com.example.case_study_module_4.repository;

import com.example.case_study_module_4.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFoodRepository extends JpaRepository<Food, Long> {
    List<Food> findTop8ByOrderByIdDesc();
    @Query("SELECT f FROM Food f WHERE LOWER(f.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Food> searchByTitle(String keyword);

    @Query("SELECT f FROM Food f JOIN f.menuRestaurants mr WHERE mr.restaurant.id = :restaurantId")
    List<Food> findByRestaurantId(@Param("restaurantId") Long restaurantId);
    @Query("SELECT f FROM Food f ORDER BY f.id DESC")
    List<Food> findTop5NewestFoods();

}
