package com.example.case_study_module_4.controller;

import com.example.case_study_module_4.service.IFoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/foods") // ✅ tất cả URL liên quan tới khách hàng
public class FoodController {

    private final IFoodService foodService;

    // Danh sách món ăn cho khách hàng
    @GetMapping
    public String showFoodTop(Model model) {
        model.addAttribute("foods", foodService.findAll());
        return "home/home";
    }

    // Chi tiết món ăn
    @GetMapping("/{id}")
    public String showFoodDetail(@PathVariable Long id, Model model) {
        var food = foodService.findById(id);
        if (food == null) {
            return "redirect:/foods";
        }
        model.addAttribute("food", food);
        return "food/detail";
    }
}
