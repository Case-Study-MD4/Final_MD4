package com.example.case_study_module_4.controller;
import com.example.case_study_module_4.entity.Food;
import com.example.case_study_module_4.entity.Order;
import com.example.case_study_module_4.entity.Restaurant;
import com.example.case_study_module_4.entity.User;
import com.example.case_study_module_4.exception.RestaurantNotFoundException;
import com.example.case_study_module_4.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.case_study_module_4.service.IRestaurantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Controller
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantController {

    private final IRestaurantService restaurantService;
    private final IFoodService foodService;
    private final IOrderService orderService;
    private final IUserService userService;
    private final ICategoriesService categoryService;

    // ========== 1. Danh sách nhà hàng ==========
    @GetMapping
    public String showRestaurantList(Model model) {
        model.addAttribute("restaurants", restaurantService.findAll());
        return "restaurant/list"; // templates/restaurant/list.html
    }

    // ========== 2. Xem thực đơn 1 nhà hàng ==========
    @GetMapping("/{id}")
    public String viewRestaurantMenu(@PathVariable Long id, Model model) {
        Restaurant restaurant = java.util.Optional.ofNullable(restaurantService.findById(id))
                .orElseThrow(() -> new RestaurantNotFoundException(id));

        model.addAttribute("restaurant", restaurant);
        model.addAttribute("foods", restaurant.getFoods());
        return "restaurant/menu";
    }


    // ========== 3. Trang dashboard chủ nhà hàng ==========
    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        Restaurant restaurant = restaurantService.findByAccountId(user.getAccount().getId());
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("user", user);
        return "restaurant/dashboard";
    }

    // ========== 4. Quản lý món ăn ==========
    @GetMapping("/foods")
    public String showFoodList(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByUsername(userDetails.getUsername());
        Restaurant restaurant = restaurantService.findByAccountId(user.getAccount().getId());
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("foods", foodService.findByRestaurantId(restaurant.getId()));
        return "restaurant/foods";
    }

    // ========== 5. Thêm món ==========
    @GetMapping("/foods/new")
    public String showAddForm(Model model) {
        model.addAttribute("food", new Food());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("action", "/restaurant/foods");
        return "restaurant/food-form";
    }

    @PostMapping("/foods")
    public String saveFood(@ModelAttribute Food food,
                           @RequestParam("categoryId") Integer categoryId,
                           @AuthenticationPrincipal UserDetails userDetails) {

        User user = userService.findByUsername(userDetails.getUsername());
        Restaurant restaurant = restaurantService.findByAccountId(user.getAccount().getId());

        food.setCategories(categoryService.findById(categoryId));

        // Lưu món
        foodService.save(food);

        // Gắn món đó vào nhà hàng (thông qua bảng trung gian)
        restaurant.getFoods().add(food);
        restaurantService.save(restaurant);

        return "redirect:/restaurant/foods";
    }


    // ========== 6. Chỉnh sửa món ==========
    @GetMapping("/foods/edit/{id}")
    public String editFood(@PathVariable Long id, Model model) {
        model.addAttribute("food", foodService.findById(id));
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("action", "/restaurant/foods/update/" + id);
        return "restaurant/food-form";
    }

    @PostMapping("/foods/update/{id}")
    public String updateFood(@PathVariable Long id,
                             @ModelAttribute Food food,
                             @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        Restaurant restaurant = restaurantService.findByAccountId(user.getAccount().getId());
        food.setId(id);
        food.setRestaurants(Collections.singletonList(restaurant));
        foodService.save(food);
        return "redirect:/restaurant/foods";
    }

    // ========== 7. Xóa món ==========
    @GetMapping("/foods/delete/{id}")
    public String deleteFood(@PathVariable Long id) {
        foodService.delete(id);
        return "redirect:/restaurant/foods";
    }

    // ========== 8. Quản lý đơn hàng ==========
    @GetMapping("/orders")
    public String viewOrders(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByUsername(userDetails.getUsername());
        Restaurant restaurant = restaurantService.findByAccountId(user.getAccount().getId());
        model.addAttribute("orders", orderService.findByRestaurantId(restaurant.getId()));
        return "restaurant/orders";
    }

    @PostMapping("/orders/{id}/complete")
    public String markOrderCompleted(@PathVariable Long id) {
        orderService.markCompleted(id);
        return "redirect:/restaurant/orders";
    }

    @PostMapping("/orders/{id}/next")
    public String nextStatus(@PathVariable Long id) {
        Order order = orderService.findById(id);
        if (order != null) {
            switch (order.getStatus()) {
                case 0 -> order.setStatus(1);
                case 1 -> order.setStatus(2);
            }
            orderService.save(order);
        }
        return "redirect:/restaurant/orders";
    }

    @PostMapping("/orders/{id}/cancel")
    public String cancelOrder(@PathVariable Long id) {
        Order order = orderService.findById(id);
        if (order != null && order.getStatus() != 2) {
            order.setStatus(3); // Hủy
            orderService.save(order);
        }
        return "redirect:/restaurant/orders";
    }


}
