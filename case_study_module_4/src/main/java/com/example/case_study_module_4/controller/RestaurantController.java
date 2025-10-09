package com.example.case_study_module_4.controller;

import com.example.case_study_module_4.entity.Food;
import com.example.case_study_module_4.entity.Restaurant;
import com.example.case_study_module_4.service.IFoodService;
import com.example.case_study_module_4.service.IRestaurantService;
import com.example.case_study_module_4.service.impl.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class RestaurantController {
    @Autowired
    private IRestaurantService restaurantService;


    @Autowired
    private FoodService foodService;

    @GetMapping("/restaurants")
    public String showFoodList(Model model) {
        model.addAttribute("restaurants", restaurantService.findAll());
        return "restaurant/list";
    }

}
