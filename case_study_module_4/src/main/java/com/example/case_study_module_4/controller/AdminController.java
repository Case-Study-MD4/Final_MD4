package com.example.case_study_module_4.controller;

import com.example.case_study_module_4.dto.RevenueDto;
import com.example.case_study_module_4.entity.Restaurant;
import com.example.case_study_module_4.service.IRestaurantService;
import com.example.case_study_module_4.service.IUserService;
import com.example.case_study_module_4.service.impl.RevenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/profile/admin")
@RequiredArgsConstructor
public class AdminController {

    private final IRestaurantService restaurantService;
    private final IUserService userService;
    private final RevenueService revenueService;


    /* ==================== QUẢN LÝ NHÀ HÀNG ==================== */

    /** Danh sách nhà hàng **/
    @GetMapping("/restaurants")
    public String viewRestaurants(Model model) {
        model.addAttribute("restaurants", restaurantService.findAll());
        return "admin/admin_restaurant";
    }

    /** Xem chi tiết nhà hàng **/
    @GetMapping("/restaurant/view/{id}")
    public String viewRestaurantDetail(@PathVariable Long id, Model model) {
        Restaurant restaurant = restaurantService.findById(id);
        if (restaurant == null) {
            return "redirect:/profile/admin/restaurants?error=notfound";
        }
        model.addAttribute("restaurant", restaurant);
        return "admin/admin_restaurant_view";
    }

    /** Form sửa nhà hàng **/
    @GetMapping("/restaurant/edit/{id}")
    public String editRestaurantForm(@PathVariable Long id, Model model) {
        Restaurant restaurant = restaurantService.findById(id);
        if (restaurant == null) {
            return "redirect:/profile/admin/restaurants?error=notfound";
        }
        model.addAttribute("restaurant", restaurant);
        return "admin/admin_restaurant_edit";
    }

    /** Cập nhật nhà hàng **/
    @PostMapping("/restaurant/update")
    public String updateRestaurant(@ModelAttribute("restaurant") Restaurant restaurant) {
        restaurantService.save(restaurant);
        return "redirect:/profile/admin/restaurants?success=updated";
    }

    /** Xoá nhà hàng (và tất cả món ăn liên quan) **/
    @PostMapping("/restaurant/delete/{id}")
    public String deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurantAndFoods(id);
        return "redirect:/profile/admin/restaurants?success=deleted";
    }

    /* ==================== QUẢN LÝ NGƯỜI DÙNG ==================== */

    @GetMapping("/users")
    public String viewUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/admin_user";
    }

    @GetMapping("/revenue")
    public String showRevenue(Model model) {
        List<RevenueDto> revenues = revenueService.getRevenueStatistics();
        BigDecimal totalRevenue = revenueService.getTotalRevenue(revenues);
        int restaurantCount = revenueService.getRestaurantCount(revenues);

        model.addAttribute("revenues", revenues);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("restaurantCount", restaurantCount);
        model.addAttribute("newCustomers", 45); // hardcode tạm

        return "admin/admin_revenue";
    }
}
