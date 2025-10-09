package com.example.case_study_module_4.service.impl;

import com.example.case_study_module_4.entity.Food;
import com.example.case_study_module_4.repository.IFoodRepository;
import com.example.case_study_module_4.service.IFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodService implements IFoodService {

    @Autowired
    private IFoodRepository foodRepository;

    public List<Food> findTopFoods() {
        return foodRepository.findTop8ByOrderByIdDesc();
    }

    @Override
    public List<Food> findAll() {
        return foodRepository.findAll();
    }

    @Override
    public Food findById(Long id) {
        return foodRepository.findById(id).orElse(null);
    }

    @Override
    public Food save(Food food) {
        return foodRepository.save(food);
    }

    @Override
    public void delete(Long id) {
     foodRepository.deleteById(id);
    }

    @Override
    public List<Food> searchByTitle(String keyword) {
        return foodRepository.searchByTitle(keyword);
    }



}
