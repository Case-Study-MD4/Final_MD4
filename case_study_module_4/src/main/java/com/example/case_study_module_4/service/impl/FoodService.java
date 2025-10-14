package com.example.case_study_module_4.service.impl;

import com.example.case_study_module_4.entity.Food;
import com.example.case_study_module_4.repository.IFoodRepository;
import com.example.case_study_module_4.repository.IRestaurantRepository;
import com.example.case_study_module_4.service.IFoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodService implements IFoodService {

    private final IFoodRepository foodRepository;
    private final IRestaurantRepository restaurantRepository;
    private final CloudinaryService cloudinaryService;

    @Override
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

    @Override
    public List<Food> findByRestaurantId(Long restaurantId) {
        return foodRepository.findByRestaurantId(restaurantId);
    }


}
