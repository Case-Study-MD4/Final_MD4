package com.example.case_study_module_4.controller;

import com.example.case_study_module_4.dto.RevenueDto;
import com.example.case_study_module_4.dto.UserDto;
import com.example.case_study_module_4.entity.Restaurant;
import com.example.case_study_module_4.entity.User;
import com.example.case_study_module_4.exception.RestaurantNotFoundException;
import com.example.case_study_module_4.exception.UserNotFoundException;
import com.example.case_study_module_4.service.IRestaurantService;
import com.example.case_study_module_4.service.IUserService;
import com.example.case_study_module_4.service.impl.RevenueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/profile/admin")
@RequiredArgsConstructor
public class AdminController {

    private final IRestaurantService restaurantService;
    private final IUserService userService;
    private final RevenueService revenueService;

    /* ==================== QUẢN LÝ NHÀ HÀNG ==================== */

    /** Danh sách nhà hàng */
    @GetMapping("/restaurants")
    public String viewRestaurants(Model model) {
        model.addAttribute("restaurants", restaurantService.findAll());
        return "admin/admin_restaurant";
    }

    /** Xem chi tiết nhà hàng */
    @GetMapping("/restaurant/view/{id}")
    public String viewRestaurantDetail(@PathVariable Long id, Model model) {
        Restaurant restaurant = java.util.Optional.ofNullable(restaurantService.findById(id))
                .orElseThrow(() -> new RestaurantNotFoundException(id));
        model.addAttribute("restaurant", restaurant);
        return "admin/admin_restaurant_view";
    }

    /** Form sửa nhà hàng */
    @GetMapping("/restaurant/edit/{id}")
    public String editRestaurantForm(@PathVariable Long id, Model model) {
        Restaurant restaurant = java.util.Optional.ofNullable(restaurantService.findById(id))
                .orElseThrow(() -> new RestaurantNotFoundException(id));
        model.addAttribute("restaurant", restaurant);
        return "admin/admin_restaurant_edit";
    }

    /** Cập nhật nhà hàng */
    @PostMapping("/restaurant/update")
    public String updateRestaurant(@ModelAttribute("restaurant") Restaurant restaurant,
                                   RedirectAttributes ra) {
        restaurantService.save(restaurant);
        ra.addFlashAttribute("success", "Cập nhật nhà hàng thành công!");
        return "redirect:/profile/admin/restaurants";
    }

    /** Xoá nhà hàng (kèm món liên quan) */
    @PostMapping("/restaurant/delete/{id}")
    public String deleteRestaurant(@PathVariable Long id, RedirectAttributes ra) {
        // ném lỗi nếu không tồn tại
        java.util.Optional.ofNullable(restaurantService.findById(id))
                .orElseThrow(() -> new RestaurantNotFoundException(id));

        restaurantService.deleteRestaurantAndFoods(id);
        ra.addFlashAttribute("success", "Đã xoá nhà hàng #" + id);
        return "redirect:/profile/admin/restaurants";
    }

    // ===== QUẢN LÝ NGƯỜI DÙNG =====

    @GetMapping("/users")
    public String viewUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/admin_user";
    }

    @GetMapping("/users/{id}")
    public String viewUserDetail(@PathVariable Long id, Model model) {
        User user = userService.getByIdOrThrow(id);
        model.addAttribute("user", user);
        return "profile/detail";
    }

    @GetMapping("/users/{id}/edit")
    public String showUserEditForm(@PathVariable Long id, Model model) {
        User user = userService.getByIdOrThrow(id);
        UserDto dto = new UserDto();
        dto.setFullName(user.getFullname());
        dto.setPhone(user.getPhone());
        dto.setAddress(user.getAddress());
        model.addAttribute("userId", id);
        model.addAttribute("form", dto);
        return "profile/edit";
    }

    @PostMapping("/users/{id}/edit")
    public String updateUser(@PathVariable Long id,
                             @Valid @ModelAttribute("form") UserDto dto,
                             BindingResult binding,
                             Model model,
                             RedirectAttributes ra) {
        if (binding.hasErrors()) {
            model.addAttribute("userId", id);
            return "profile/edit";
        }
        userService.updateProfile(id, dto);
        ra.addFlashAttribute("success", "Cập nhật người dùng thành công!");
        return "redirect:/profile/admin/users/" + id;
    }

    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable Long id, RedirectAttributes ra) {
        userService.deleteById(id);
        ra.addFlashAttribute("success", "Đã xoá người dùng #" + id);
        return "redirect:/profile/admin/users";
    }


    /* ==================== DOANH THU ==================== */
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
