package com.example.case_study_module_4.service.impl;

import com.example.case_study_module_4.dto.CartItemDto;
import com.example.case_study_module_4.entity.Food;
import com.example.case_study_module_4.service.ICartService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {

    private final HttpSession session;

    @SuppressWarnings("unchecked")
    public List<CartItemDto> getCart() {
        List<CartItemDto> cart = (List<CartItemDto>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    public void addToCart(Food food, int quantity) {
        List<CartItemDto> cart = getCart();
        for (CartItemDto item : cart) {
            if (item.getFoodId().equals(food.getId())) {
                item.setQuantity(item.getQuantity() + quantity);
                session.setAttribute("cart", cart);
                return;
            }
        }
        cart.add(new CartItemDto(food.getId(), food.getTitle(), food.getPrice(), quantity));
        session.setAttribute("cart", cart);
    }

    public void remove(Long foodId) {
        List<CartItemDto> cart = getCart();
        cart.removeIf(item -> item.getFoodId().equals(foodId));
        session.setAttribute("cart", cart);
    }

    public void clear() {
        session.removeAttribute("cart");
    }

    public BigDecimal getTotal() {
        return getCart().stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public void updateQuantity(Long foodId, int quantity) {
        List<CartItemDto> cart = getCart(); // 👈 lấy giỏ từ session
        for (CartItemDto item : cart) {
            if (item.getFoodId().equals(foodId)) {
                item.setQuantity(quantity);
                break;
            }
        }
        session.setAttribute("cart", cart); // cập nhật lại session
    }

    @Override
    public double getItemSubtotal(Long foodId) {
        List<CartItemDto> cart = getCart(); // 👈 lấy giỏ từ session
        for (CartItemDto item : cart) {
            if (item.getFoodId().equals(foodId)) {
                return item.getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity()))
                        .doubleValue();
            }
        }
        return 0;
    }


}
