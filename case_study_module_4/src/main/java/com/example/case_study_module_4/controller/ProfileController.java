package com.example.case_study_module_4.controller;

import com.example.case_study_module_4.entity.Restaurant;
import com.example.case_study_module_4.service.impl.CustomerService;
import com.example.case_study_module_4.service.impl.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {
    private final RestaurantService restaurantService;
    private final CustomerService customerService;
    @GetMapping("/admin")
    public String dashboard(Model model) {
        model.addAttribute("restaurants", restaurantService.findAll());
        model.addAttribute("restaurant", new Restaurant());
        model.addAttribute("totalRevenue", restaurantService.getTotalRevenue());
        model.addAttribute("restaurantCount", restaurantService.countActive());
        model.addAttribute("customerCount", customerService.getCustomerCount());
        model.addAttribute("ownerCount", customerService.getOwnerCount());
        return "profile/admin";

    }

    @PostMapping("/admin/restaurant/save")
    public String save(@ModelAttribute Restaurant restaurant) {
        restaurantService.save(restaurant);
        return "redirect:/profile/admin";
    }

}
