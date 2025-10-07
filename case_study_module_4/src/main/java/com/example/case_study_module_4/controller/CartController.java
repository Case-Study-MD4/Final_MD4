package com.example.case_study_module_4.controller;

import com.example.case_study_module_4.dto.CartItemDto;
import com.example.case_study_module_4.dto.CreateOrderDto;
import com.example.case_study_module_4.entity.Food;
import com.example.case_study_module_4.entity.Order;
import com.example.case_study_module_4.repository.IFoodRepository;
import com.example.case_study_module_4.service.IOrderService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final IOrderService orderService;
    private final IFoodRepository foodRepository;

    // ✅ Hiển thị giỏ hàng
    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        List<CartItemDto> cart = (List<CartItemDto>) session.getAttribute("cart");
        if (cart == null) cart = new ArrayList<>();

        BigDecimal total = cart.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("cartItems", cart);
        model.addAttribute("total", total);
        return "cart/view";
    }

    // ✅ Thêm món vào giỏ
    @PostMapping("/add")
    public String addToCart(@RequestParam("foodId") Long foodId,
                            @RequestParam("quantity") int quantity,
                            HttpSession session) {

        // 1️⃣ Lấy thông tin món ăn từ DB
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy món ăn"));

        // 2️⃣ Lấy giỏ hàng hiện tại
        List<CartItemDto> cart = (List<CartItemDto>) session.getAttribute("cart");
        if (cart == null) cart = new ArrayList<>();

        // 3️⃣ Nếu món đã có trong giỏ thì tăng số lượng
        boolean exists = false;
        for (CartItemDto item : cart) {
            if (item.getFoodId().equals(foodId)) {
                item.setQuantity(item.getQuantity() + quantity);
                exists = true;
                break;
            }
        }

        // 4️⃣ Nếu chưa có thì thêm mới
        if (!exists) {
            cart.add(new CartItemDto(
                    food.getId(),
                    food.getTitle(),
                    food.getPrice(),
                    quantity
            ));
        }

        // 5️⃣ Lưu lại vào session
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
        session.removeAttribute("cart");

        return "redirect:/orders/success/" + order.getId();
    }
}
