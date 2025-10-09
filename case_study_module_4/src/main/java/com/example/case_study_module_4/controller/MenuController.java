package com.example.case_study_module_4.controller;
import com.example.case_study_module_4.dto.CartItemDto;
import com.example.case_study_module_4.entity.Food;
import com.example.case_study_module_4.entity.MenuRestaurant;
import com.example.case_study_module_4.entity.MenuRestaurantId;
import com.example.case_study_module_4.entity.Restaurant;
import com.example.case_study_module_4.repository.IFoodRepository;
import com.example.case_study_module_4.repository.IMenuRestaurantRepository;
import com.example.case_study_module_4.repository.IRestaurantRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
<<<<<<< HEAD

=======
>>>>>>> f50d4f65c668578eba81aed10319dc97ac4479d3
@Controller
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {
<<<<<<< HEAD

    private final IFoodRepository foodRepository;
    private final IRestaurantRepository restaurantRepository;
    private final IMenuRestaurantRepository menuRestaurantRepository;

=======
    private final IFoodRepository foodRepository;
    private final IRestaurantRepository restaurantRepository;
    private final IMenuRestaurantRepository menuRestaurantRepository;
>>>>>>> f50d4f65c668578eba81aed10319dc97ac4479d3
    @GetMapping
    public String showMenu(Model model, HttpSession session) {
        List<MenuRestaurant> menuList = menuRestaurantRepository.findAll();
        model.addAttribute("menuList", menuList);
        List<CartItemDto> cart = (List<CartItemDto>) session.getAttribute("cart");
        if (cart == null) cart = new ArrayList<>();
        model.addAttribute("cartItems", cart);
        // Tính tổng với BigDecimal
        BigDecimal total = cart.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("total", total);
        return "food/menu";
    }
    // :white_check_mark: Thêm món vào giỏ hàng
    @PostMapping("/add-to-cart")
    @ResponseBody
    public Map<String, Object> addToCart(@RequestParam Long foodId,
                                         @RequestParam(defaultValue = "1") int quantity,
                                         HttpSession session) {
        List<CartItemDto> cart = (List<CartItemDto>) session.getAttribute("cart");
        if (cart == null) cart = new ArrayList<>();
        // Lấy món ăn
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy món ăn"));
        // :white_check_mark: Lấy nhà hàng từ món
        Restaurant restaurant = food.getRestaurant();
        if (restaurant == null) {
            throw new RuntimeException("Món ăn chưa có nhà hàng liên kết!");
        }
        boolean found = false;
        for (CartItemDto item : cart) {
            if (item.getFoodId().equals(foodId)) {
                item.setQuantity(item.getQuantity() + quantity);
                found = true;
                break;
            }
        }
        if (!found) {
            CartItemDto newItem = new CartItemDto();
            newItem.setFoodId(foodId);
            newItem.setFoodName(food.getTitle());
            newItem.setPrice(BigDecimal.valueOf(food.getPrice()));
            newItem.setQuantity(quantity);
            newItem.setRestaurantId(restaurant.getId());
            cart.add(newItem);
        }
        session.setAttribute("cart", cart);
        return Map.of(
                "success", true,
                "cart", cart,
                "restaurantId", restaurant.getId()
        );
    }
    //    @GetMapping("/add_food")
    public String addFoodForm(Model model) {
        model.addAttribute("food", new Food());
        model.addAttribute("restaurants", restaurantRepository.findAll());
        return "food/add_food";
    }
    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    @PostMapping("/add_food")
    public String addFood(@ModelAttribute("food") Food food,
                          @RequestParam Long restaurantId,
                          BindingResult bindingResult,
                          RedirectAttributes redirect) {
        if (bindingResult.hasErrors()) {
            return "food/add_food";
        }
        Food savedFood = foodRepository.save(food);
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Nhà hàng không tồn tại"));
        MenuRestaurant menuRestaurant = new MenuRestaurant();
        MenuRestaurantId menuId = new MenuRestaurantId(savedFood.getId(), restaurant.getId());
        menuRestaurant.setId(menuId);
        menuRestaurant.setFood(savedFood);
        menuRestaurant.setRestaurant(restaurant);
        menuRestaurantRepository.save(menuRestaurant);
        redirect.addFlashAttribute("message", "Thêm món mới thành công!");
        return "redirect:/menu";
    }
}
