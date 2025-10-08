package com.example.case_study_module_4.repository;

import com.example.case_study_module_4.entity.MenuRestaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IMenuRestaurantRepository extends JpaRepository<MenuRestaurant, Long> {
    List<MenuRestaurant> findAllByFoodId(Long foodId);

}