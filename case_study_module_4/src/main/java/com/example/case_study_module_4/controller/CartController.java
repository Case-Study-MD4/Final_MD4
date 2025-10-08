package com.example.case_study_module_4.controller;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @PostMapping("/add")
    public String addToCart(@RequestParam("foodId") Long foodId,
                            @RequestParam("quantity") int quantity,
                            RedirectAttributes redirect) {

        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy món ăn"));

        // Lấy danh sách menu liên quan đến món ăn
        List<MenuRestaurant> menuList = menuRestaurantRepository.findAllByFoodId(foodId);
        if (menuList.isEmpty()) {
            redirect.addFlashAttribute("error", "Món này chưa được gán cho nhà hàng nào!");
            return "redirect:/menu";
        }

        // Lấy nhà hàng đầu tiên (hoặc bạn có thể yêu cầu chọn)
        Long restaurantId = menuList.get(0).getRestaurant().getId();

        // Kiểm tra giỏ hàng
        if (!cartService.getCart().isEmpty()) {
            Long existingRestaurantId = cartService.getCart().stream()
                    .findFirst()
                    .map(item -> menuRestaurantRepository.findAllByFoodId(item.getFoodId())
                            .get(0).getRestaurant().getId())
                    .orElse(null);

            if (!existingRestaurantId.equals(restaurantId)) {
                redirect.addFlashAttribute("error", "Bạn chỉ có thể đặt món từ 1 nhà hàng trong cùng đơn hàng!");
                return "redirect:/cart";
            }
        }


        cartService.addToCart(food, quantity);
        return "redirect:/cart";
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
        model.addAttribute("restaurantId", restaurantId);
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

        // ✅ Cập nhật số lượng trong cartService
        for (int i = 0; i < foodIds.size(); i++) {
            cartService.updateQuantity(foodIds.get(i), quantities.get(i));
        }

        // ✅ Tạo đơn hàng
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
