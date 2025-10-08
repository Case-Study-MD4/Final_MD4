package com.example.case_study_module_4.controller;

import com.example.case_study_module_4.dto.CartItemDto;
import com.example.case_study_module_4.entity.Food;
import com.example.case_study_module_4.repository.IFoodRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    private final IFoodRepository foodRepository;

    @GetMapping
    public String showMenu(Model model) {
        List<Food> foods = foodRepository.findAll();
        model.addAttribute("foods", foods);
        return "food/menu";
    }

    // ✅ Thêm món vào giỏ hàng
    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam Long foodId, @RequestParam(defaultValue = "1") int quantity,
                            HttpSession session) {
        List<CartItemDto> cart = (List<CartItemDto>) session.getAttribute("cart");
        if (cart == null) cart = new ArrayList<>();

        // Kiểm tra xem món đã có trong giỏ chưa
        boolean found = false;
        for (CartItemDto item : cart) {
            if (item.getFoodId().equals(foodId)) {
                item.setQuantity(item.getQuantity() + quantity);
                found = true;
                break;
            }
        }

        if (!found) {
            cart.add(new CartItemDto(foodId, quantity));
        }

        session.setAttribute("cart", cart);
        return "redirect:/cart";
    }

    @GetMapping("/add_food")
    public String addFood(Model model) {
        model.addAttribute("foods", new Food());
        return "food/add_food";
    }

    @PostMapping("/add_food")
    public String addFood(@ModelAttribute("food") Food food, BindingResult bindingResult, RedirectAttributes redirect) {
        if (bindingResult.hasErrors()) {
            return "food/add_food";
        }
        foodRepository.save(food);
        redirect.addFlashAttribute("message", "Thêm mới thành công");
        return "redirect:/menu";
    }
}
