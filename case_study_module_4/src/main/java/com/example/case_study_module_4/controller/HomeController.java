package com.example.case_study_module_4.controller;

import com.example.case_study_module_4.entity.Food;
import com.example.case_study_module_4.entity.Restaurant;
import com.example.case_study_module_4.service.impl.FoodService;
import com.example.case_study_module_4.service.impl.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/home")
    public String showHome(Model model) {
        model.addAttribute("foods", foodService.findTopFoods());
        model.addAttribute("restaurants", restaurantService.findTopRestaurants());
        return "home/home";
    }

    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword, Model model) {
        List<Food> foods = foodService.searchByTitle(keyword);
        List<Restaurant> restaurants = restaurantService.searchByTitle(keyword);

        model.addAttribute("keyword", keyword);
        model.addAttribute("foods", foods);
        model.addAttribute("restaurants", restaurants);

        return "home/result";
    }
}

