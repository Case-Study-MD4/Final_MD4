package com.example.case_study_module_4.controller;
import com.example.case_study_module_4.dto.CartItemDto;
import com.example.case_study_module_4.dto.CreateOrderDto;
import com.example.case_study_module_4.entity.Food;
import com.example.case_study_module_4.entity.MenuRestaurant;
import com.example.case_study_module_4.entity.Order;
import com.example.case_study_module_4.entity.User;
import com.example.case_study_module_4.repository.IFoodRepository;
import com.example.case_study_module_4.repository.IMenuRestaurantRepository;
import com.example.case_study_module_4.repository.IRestaurantRepository;
import com.example.case_study_module_4.repository.IUserRepository;
import com.example.case_study_module_4.service.ICartService;
import com.example.case_study_module_4.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final IOrderService orderService;
    private final IFoodRepository foodRepository;
    private final IUserRepository userRepository;
    private final ICartService cartService;
    private final IRestaurantRepository restaurantRepository;
    private final IMenuRestaurantRepository menuRestaurantRepository;
    @PostMapping("/add-to-cart")
    @ResponseBody
    public Map<String, Object> addToCartAjax(@RequestParam Long foodId,
                                             @RequestParam int quantity) {
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy món ăn"));
        cartService.addToCart(food, quantity);
        List<CartItemDto> cart = cartService.getCart();
        Long restaurantId = null;
        if(!cart.isEmpty()){
            Long firstFoodId = cart.get(0).getFoodId();
            List<MenuRestaurant> menuList = menuRestaurantRepository.findAllByFoodId(firstFoodId);
            if(!menuList.isEmpty()){
                restaurantId = menuList.get(0).getRestaurant().getId();
            }
        }
        Map<String, Object> resp = new HashMap<>();
        resp.put("cart", cart);
        resp.put("restaurantId", restaurantId);
        return resp;
    }

    @GetMapping
    public String viewCart(Model model) {
        var cartItems = cartService.getCart();
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", cartService.getTotal());
        Long restaurantId = null;
        if (!cartItems.isEmpty()) {
            Long firstFoodId = cartItems.get(0).getFoodId();
            List<MenuRestaurant> menuList = menuRestaurantRepository.findAllByFoodId(firstFoodId);
            if (!menuList.isEmpty()) {
                restaurantId = menuList.get(0).getRestaurant().getId();
            }
        }
        model.addAttribute("restaurantId", restaurantId); // :white_check_mark: đảm bảo không null
        return "cart/view";
    }

    @PostMapping("/checkout")
    public String checkout(@RequestParam Long restaurantId,
                           @RequestParam List<Long> foodIds,
                           @RequestParam List<Integer> quantities) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByAccount_UserName(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
        // :white_check_mark: Cập nhật số lượng trong cartService
        for (int i = 0; i < foodIds.size(); i++) {
            cartService.updateQuantity(foodIds.get(i), quantities.get(i));
        }
        // :white_check_mark: Tạo đơn hàng
        CreateOrderDto dto = new CreateOrderDto();
        dto.setUserId(user.getId());
        dto.setRestaurantId(restaurantId);
        dto.setItems(cartService.getCart());
        Order order = orderService.createOrder(dto);
        cartService.clear();
        return "redirect:/orders/success/" + order.getId();
    }
    @PostMapping("/update")
    @ResponseBody
    public Map<String, Object> updateQuantity(@RequestParam("foodId") Long foodId,
                                              @RequestParam("quantity") int quantity) {
        cartService.updateQuantity(foodId, quantity);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("total", cartService.getTotal());
        response.put("itemSubtotal", cartService.getItemSubtotal(foodId));
        return response;
    }
}

