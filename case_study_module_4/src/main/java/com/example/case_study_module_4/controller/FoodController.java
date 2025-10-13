package com.example.case_study_module_4.controller;

import com.example.case_study_module_4.service.IFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class FoodController {

    @Autowired
    private IFoodService foodService;


    @GetMapping("/foods")
    public String showFoodTop(Model model) {
        model.addAttribute("foods", foodService.findAll());
        return "home/home";
    }
    @GetMapping("/food/{id}")
    public String showFoodDetail(@PathVariable Long id, Model model) {
        var food = foodService.findById(id);
        if (food == null) {
            return "redirect:/foods";
        }
        model.addAttribute("food", food);
        return "food/detail";
    }
}
