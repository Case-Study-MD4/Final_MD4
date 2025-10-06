package com.example.case_study_module_4.controller;

import com.example.case_study_module_4.dto.CartItemDto;
import com.example.case_study_module_4.dto.CreateOrderDto;
import com.example.case_study_module_4.entity.Order;
import com.example.case_study_module_4.service.IOrderService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final IOrderService orderService;

    // ✅ Hiển thị giỏ hàng
    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        List<CartItemDto> cart = (List<CartItemDto>) session.getAttribute("cart");
        if (cart == null) cart = new ArrayList<>();

        model.addAttribute("cartItems", cart);
        model.addAttribute("total", cart.stream()
                .map(item -> item.getPrice().multiply(java.math.BigDecimal.valueOf(item.getQuantity())))
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add));

        return "cart/view";
    }

    // ✅ Thêm món vào giỏ
    @PostMapping("/add")
    public String addToCart(@ModelAttribute CartItemDto item, HttpSession session) {
        List<CartItemDto> cart = (List<CartItemDto>) session.getAttribute("cart");
        if (cart == null) cart = new ArrayList<>();

        boolean exists = false;
        for (CartItemDto i : cart) {
            if (i.getFoodId().equals(item.getFoodId())) {
                i.setQuantity(i.getQuantity() + item.getQuantity());
                exists = true;
                break;
            }
        }
        if (!exists) cart.add(item);

        session.setAttribute("cart", cart);
        return "redirect:/cart";
    }

    // ✅ Xoá món khỏi giỏ
    @GetMapping("/remove/{foodId}")
    public String removeItem(@PathVariable Long foodId, HttpSession session) {
        List<CartItemDto> cart = (List<CartItemDto>) session.getAttribute("cart");
        if (cart != null) {
            cart.removeIf(item -> item.getFoodId().equals(foodId));
            session.setAttribute("cart", cart);
        }
        return "redirect:/cart";
    }

    // ✅ Đặt hàng
    @PostMapping("/checkout")
    public String checkout(@RequestParam Long restaurantId, HttpSession session) {
        List<CartItemDto> cart = (List<CartItemDto>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) return "redirect:/cart";

        CreateOrderDto dto = new CreateOrderDto();
        dto.setUserId(1L); // giả lập user login
        dto.setRestaurantId(restaurantId);
        dto.setItems(cart);

        Order order = orderService.createOrder(dto);

        // xoá giỏ hàng sau khi đặt
        session.removeAttribute("cart");

        return "redirect:/orders/" + order.getId();
    }
}
