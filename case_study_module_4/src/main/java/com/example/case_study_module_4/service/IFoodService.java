package com.example.case_study_module_4.service;

import com.example.case_study_module_4.entity.Food;
import com.example.case_study_module_4.entity.Restaurant;

import java.util.List;
import java.util.Optional;

public interface IFoodService {
    List<Food> findTopFoods();
    List<Food> findAll();
    Food findById(Long id);
    Food save(Food food);
    void delete(Long id);
    List<Food> searchByTitle(String keyword);
}
