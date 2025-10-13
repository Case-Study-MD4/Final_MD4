package com.example.case_study_module_4.service.impl;

import com.example.case_study_module_4.entity.Restaurant;
import com.example.case_study_module_4.repository.IRestaurantRepository;
import com.example.case_study_module_4.service.IRestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService implements IRestaurantService {

    @Autowired
    private IRestaurantRepository restaurantRepository;

    @Override
    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }

    @Override
    public Restaurant findById(Long id) {
        return restaurantRepository.findById(id).orElse(null);
    }

    @Override
    public List<Restaurant> findTopRestaurants() {
        return restaurantRepository.findTop8ByOrderByIdDesc();
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void delete(Long id) {
        restaurantRepository.deleteById(id);
    }
    @Override
    public List<Restaurant> searchByTitle(String keyword) {
        return restaurantRepository.searchByTitle(keyword);
    }

    @Override
    public Restaurant findByAccountId(Long accountId) {
        return restaurantRepository.findByAccountId(accountId);
    }
}
