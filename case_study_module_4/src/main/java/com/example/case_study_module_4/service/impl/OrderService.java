package com.example.case_study_module_4.service.impl;

import com.example.case_study_module_4.dto.CartItemDto;
import com.example.case_study_module_4.dto.CreateOrderDto;
import com.example.case_study_module_4.entity.Order;
import com.example.case_study_module_4.entity.OrderItem;
import com.example.case_study_module_4.repository.*;
import com.example.case_study_module_4.service.IOrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final IOrderRepository orderRepository;
    private final IFoodRepository foodRepository;
    private final IUserRepository userRepository;
    private final IOrderItemRepository itemRepository;
    private final IRestaurantRepository restaurantRepository;

    @Override
    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    @Transactional
    public Order createOrder(CreateOrderDto dto) {
        // 1️⃣ Tìm user
        var user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        // 2️⃣ Tạo order
        var restaurant = restaurantRepository.findById(dto.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhà hàng"));

        Order order = new Order();
        order.setUser(user);
        order.setRestaurant(restaurant);
        order.setStatus(0); // 0 = chờ xác nhận
        order.setTotalPrice(BigDecimal.ZERO);

        BigDecimal total = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        // 3️⃣ Duyệt các món
        for (CartItemDto itemDto : dto.getItems()) {
            var food = foodRepository.findById(itemDto.getFoodId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy món ăn"));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setFood(food);
            orderItem.setQuantity(itemDto.getQuantity());

            BigDecimal price = food.getPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity()));
            orderItem.setPrice(price);

            total = total.add(price);
            orderItems.add(orderItem);
        }

        order.setItems(orderItems);
        order.setTotalPrice(total);

        return orderRepository.save(order);
    }

}
