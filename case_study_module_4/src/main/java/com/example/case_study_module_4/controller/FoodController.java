package com.example.case_study_module_4.controller;

import com.example.case_study_module_4.entity.Food;
import com.example.case_study_module_4.exception.FoodNotFoundException;
import com.example.case_study_module_4.service.IFoodService;
import com.example.case_study_module_4.service.impl.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/foods") // ✅ Đổi gốc để khớp với form
public class FoodController {

    private final IFoodService foodService;
    private final CloudinaryService cloudinaryService;

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
            return "redirect:/restaurant/foods";
        }
        Food food = java.util.Optional.ofNullable(foodService.findById(id))
                .orElseThrow(() -> new FoodNotFoundException(id));

        model.addAttribute("food", food);
        return "food/detail";
    }

    @PostMapping("/save")
    public String saveFood(@ModelAttribute Food food,
                           @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            // 🧠 Nếu người dùng upload ảnh mới
            if (imageFile != null && !imageFile.isEmpty()) {
                // ✅ Upload lên Cloudinary
                String imageUrl = cloudinaryService.uploadFile(imageFile);
                food.setImage(imageUrl);
            } else if (food.getId() != null) {
                // 🧠 Nếu là cập nhật món ăn, giữ ảnh cũ
                Food existing = foodService.findById(food.getId());
                if (existing != null) {
                    food.setImage(existing.getImage());
                }
            }

            // 🧠 Lưu vào DB
            foodService.save(food);
            return "redirect:/restaurant/foods";

        } catch (Exception e) {
            e.printStackTrace();
            return "403";
        }
    }


}
