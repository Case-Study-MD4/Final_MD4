package com.example.case_study_module_4.service.impl;

import com.example.case_study_module_4.entity.Order;
import com.example.case_study_module_4.entity.Restaurant;
import com.example.case_study_module_4.repository.IFoodRepository;
import com.example.case_study_module_4.repository.IMenuRestaurantRepository;
import com.example.case_study_module_4.repository.IOrderRepository;
import com.example.case_study_module_4.repository.IRestaurantRepository;
import com.example.case_study_module_4.service.IRestaurantService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService implements IRestaurantService {

    private final IRestaurantRepository restaurantRepository;
    private final IOrderRepository orderRepository;
    private final IMenuRestaurantRepository menuRestaurantRepository;
    private final IFoodRepository foodRepository;

    @Override
    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }

    @Override
    public Restaurant findById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhà hàng với ID: " + id));
    }

    @Override
    public List<Restaurant> findTopRestaurants() {
        return restaurantRepository.findTop8ByOrderByIdDesc();
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void delete(Long id) {
        restaurantRepository.deleteById(id);
    }

    @Override
    public List<Restaurant> searchByTitle(String keyword) {
        if (keyword == null || keyword.isBlank()) return Collections.emptyList();
        return restaurantRepository.searchByTitle(keyword);
    }

    @Override
    public Restaurant findByAccountId(Long accountId) {
        return restaurantRepository.findByAccountId(accountId);
    }

    /**
     * Xóa nhà hàng kèm các món ăn liên quan (nếu không được liên kết với nhà hàng khác)
     */
    @Transactional
    @Override
    public void deleteRestaurantAndFoods(Long restaurantId) {

        Restaurant restaurant = findById(restaurantId);

        // 1️⃣ Xóa tất cả liên kết trong bảng menu_restaurant
        menuRestaurantRepository.deleteByRestaurantId(restaurantId);

        // 2️⃣ Xóa các món ăn của nhà hàng này nếu món ăn không còn ở nhà hàng khác
        if (restaurant.getFoods() != null) {
            restaurant.getFoods().forEach(food -> {
                if (food.getMenuRestaurants().size() <= 1) {
                    foodRepository.delete(food);
                }
            });
        }

        // 3️⃣ Xóa nhà hàng
        restaurantRepository.delete(restaurant);
    }

    /**
     * Tính doanh thu của 1 nhà hàng
     */
    public BigDecimal getRevenueByRestaurant(Long restaurantId) {
        List<Order> orders = orderRepository.findAllByRestaurantId(restaurantId);
        if (orders == null || orders.isEmpty()) return BigDecimal.ZERO;
        return orders.stream()
                .map(Order::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Tính tổng doanh thu toàn hệ thống
     */
    public BigDecimal getTotalRevenue() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) return BigDecimal.ZERO;
        return orders.stream()
                .map(Order::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public long countActive() {
        return restaurantRepository.count();
    }
}
