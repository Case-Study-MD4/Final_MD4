package com.example.case_study_module_4.controller;
import com.example.case_study_module_4.entity.Restaurant;
import com.example.case_study_module_4.service.IRestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
@RequiredArgsConstructor
public class RestaurantController {

    private final IRestaurantService restaurantService;
    @GetMapping("/restaurants")
    public String showFoodList(Model model) {
        model.addAttribute("restaurants", restaurantService.findAll());
        return "restaurant/list";
    }

    @GetMapping("/restaurants/{id}")
    public String viewRestaurantMenu(@PathVariable Long id, Model model) {
        Restaurant restaurant = restaurantService.findById(id);
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("foods", restaurant.getFoods());
        return "restaurant/menu";
    }
}
