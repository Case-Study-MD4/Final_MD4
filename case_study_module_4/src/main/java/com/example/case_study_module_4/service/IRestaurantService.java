package com.example.case_study_module_4.service;

import com.example.case_study_module_4.entity.Restaurant;
import java.util.List;

public interface IRestaurantService {
    List<Restaurant> findAll();
    Restaurant findById(Long id);
    List<Restaurant> findTopRestaurants();
    Restaurant save(Restaurant restaurant);
    void delete(Long id);
    List<Restaurant> searchByTitle(String keyword);

    Restaurant findByAccountId(Long accountId);
}
