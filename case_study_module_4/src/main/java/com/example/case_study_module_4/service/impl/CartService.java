package com.example.case_study_module_4.service.impl;

import com.example.case_study_module_4.dto.CartItemDto;
import com.example.case_study_module_4.entity.Food;
import com.example.case_study_module_4.service.ICartService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<Map<String, Object>> getCartForJson() {
        List<Map<String, Object>> cartJson = new ArrayList<>();
        for (CartItemDto item : getCart()) {
            Map<String, Object> map = new HashMap<>();
            map.put("foodId", item.getFoodId());
            map.put("foodName", item.getFoodName());
            map.put("price", item.getPrice().doubleValue());
            map.put("quantity", item.getQuantity());
            map.put("restaurantId", item.getRestaurantId());
            cartJson.add(map);
        }
        return cartJson;
    }

    public void addToCart(Food food, int quantity) {
        if (food.getRestaurant() == null) {
            throw new RuntimeException("Món ăn chưa được gán nhà hàng!");
        }

        List<CartItemDto> cart = getCart();

        // Kiểm tra món đã có trong giỏ chưa
        for (CartItemDto item : cart) {
            if (item.getFoodId().equals(food.getId())) {
                item.setQuantity(item.getQuantity() + quantity);
                session.setAttribute("cart", cart);
                return;
            }
        }

        BigDecimal itemPrice = BigDecimal.valueOf(food.getPrice());
        cart.add(new CartItemDto(
                food.getId(),
                food.getTitle(),
                itemPrice,
                quantity,
                food.getRestaurant().getId()
        ));

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
        List<CartItemDto> cart = getCart();
        for (CartItemDto item : cart) {
            if (item.getFoodId().equals(foodId)) {
                item.setQuantity(quantity);
                break;
            }
        }
        session.setAttribute("cart", cart);
    }

    @Override
    public double getItemSubtotal(Long foodId) {
        List<CartItemDto> cart = getCart();
        for (CartItemDto item : cart) {
            if (item.getFoodId().equals(foodId)) {
                return item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())).doubleValue();
            }
        }
        return 0;
    }

}
