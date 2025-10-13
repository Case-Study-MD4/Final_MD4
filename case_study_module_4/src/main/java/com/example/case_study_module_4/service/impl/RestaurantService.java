package com.example.case_study_module_4.service.impl;

import com.example.case_study_module_4.entity.Food;
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
        return restaurantRepository.findById(id).orElse(null);
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
        return restaurantRepository.searchByTitle(keyword);
    }

    @Transactional
    @Override
    public void deleteRestaurantAndFoods(Long id) {

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhà hàng để xóa!"));

        // 1️⃣ Xóa tất cả liên kết trong bảng menu_restaurant
        menuRestaurantRepository.deleteByRestaurantId(id);

        // 2️⃣ Nếu ông bạn muốn xóa luôn các món ăn của nhà hàng này (không còn ở nơi nào khác)
        List<Food> foods = restaurant.getFoods();
        if (foods != null && !foods.isEmpty()) {
            for (Food food : foods) {
                // Kiểm tra xem món này có còn gắn nhà hàng nào khác không
                if (food.getMenuRestaurants().size() <= 1) {
                    foodRepository.delete(food);
                }
            }
        }

        // 3️⃣ Cuối cùng xóa nhà hàng
        restaurantRepository.delete(restaurant);
    }


    // Tính doanh thu theo nhà hàng
    public BigDecimal getRevenueByRestaurant(Long restaurantId) {
        List<Order> orders = orderRepository.findAllByRestaurantId(restaurantId);
        return orders.stream()
                .map(Order::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Tính tổng doanh thu toàn hệ thống
    public BigDecimal getTotalRevenue() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(Order::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public long countActive() {
        return restaurantRepository.count();
    }

}
