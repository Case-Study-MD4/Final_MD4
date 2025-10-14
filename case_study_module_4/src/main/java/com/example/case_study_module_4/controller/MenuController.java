package com.example.case_study_module_4.controller;

import com.example.case_study_module_4.dto.CartItemDto;
import com.example.case_study_module_4.entity.*;
import com.example.case_study_module_4.repository.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    private final IFoodRepository foodRepository;
    private final IRestaurantRepository restaurantRepository;
    private final IMenuRestaurantRepository menuRestaurantRepository;
    private final IOrderItemRepository orderItemRepository;


    @GetMapping
    public String showMenu(Model model, HttpSession session) {
        // ✅ Lấy tất cả món trong menu
        List<MenuRestaurant> allMenus = menuRestaurantRepository.findAllWithFoodAndRestaurant();

        // ✅ Lấy top món bán chạy nhất
        List<Object[]> topSoldFoods = orderItemRepository.findTopFoodsBySales();
        List<MenuRestaurant> featuredList = new ArrayList<>();

        for (Object[] record : topSoldFoods) {
            Long foodId = ((Number) record[0]).longValue();
            menuRestaurantRepository.findByFood_Id(foodId)
                    .ifPresent(featuredList::add);
            if (featuredList.size() >= 3) break; // chỉ lấy 3 món nổi bật
        }

        // ✅ Lấy 5 món mới nhất
        List<Food> newestFoods = foodRepository.findTop5NewestFoods();

        // ✅ Thêm vào model
        model.addAttribute("featuredList", featuredList); // 🔥 món nổi bật
        model.addAttribute("newestFoods", newestFoods);   // 🆕 món mới
        model.addAttribute("menuList", allMenus);

        // ✅ Xử lý giỏ hàng
        List<CartItemDto> cart = (List<CartItemDto>) session.getAttribute("cart");
        if (cart == null) cart = new ArrayList<>();
        model.addAttribute("cartItems", cart);

        BigDecimal total = cart.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("total", total);

        return "food/menu";
    }

    // ✅ Thêm món vào giỏ hàng
    @PostMapping("/add-to-cart")
    @ResponseBody
    public Map<String, Object> addToCart(@RequestParam Long foodId,
                                         @RequestParam(defaultValue = "1") int quantity,
                                         HttpSession session) {
        List<CartItemDto> cart = (List<CartItemDto>) session.getAttribute("cart");
        if (cart == null) cart = new ArrayList<>();

        // ✅ Lấy MenuRestaurant để xác định nhà hàng
        MenuRestaurant menu = menuRestaurantRepository.findByFood_Id(foodId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy món ăn trong menu"));
        Food food = menu.getFood();
        Restaurant restaurant = menu.getRestaurant();

        // ✅ Kiểm tra xem đã có món trong giỏ chưa
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

    @GetMapping("/add_food")
    public String addFoodForm(Model model) {
        model.addAttribute("food", new Food());
        model.addAttribute("restaurants", restaurantRepository.findAll());
        return "food/add_food";
    }

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
        MenuRestaurantId menuId = new MenuRestaurantId(restaurant.getId(), savedFood.getId()); // ✅ đúng thứ tự
        menuRestaurant.setId(menuId);
        menuRestaurant.setFood(savedFood);
        menuRestaurant.setRestaurant(restaurant);
        menuRestaurantRepository.save(menuRestaurant);

        redirect.addFlashAttribute("message", "Thêm món mới thành công!");
        return "redirect:/menu";
    }


    @PostMapping("/update-cart")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateCartItem(
            @RequestParam Long foodId,
            @RequestParam int quantity,
            @RequestParam Long restaurantId,
            HttpSession session) {

        // SỬA LẠI: dùng CartItemDto
        List<CartItemDto> cart = getOrCreateCart(session);

        // Tìm và cập nhật item
        for (CartItemDto item : cart) {
            if (item.getFoodId().equals(foodId)) {
                item.setQuantity(quantity);
                break;
            }
        }

        session.setAttribute("cart", cart);

        Map<String, Object> response = new HashMap<>();
        response.put("cart", cart);
        response.put("restaurantId", restaurantId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/remove-from-cart")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> removeFromCart(
            @RequestParam Long foodId,
            @RequestParam Long restaurantId,
            HttpSession session) {

        // SỬA LẠI: dùng CartItemDto
        List<CartItemDto> cart = getOrCreateCart(session);

        // Xóa item khỏi giỏ hàng
        cart.removeIf(item -> item.getFoodId().equals(foodId));

        session.setAttribute("cart", cart);

        Map<String, Object> response = new HashMap<>();
        response.put("cart", cart);
        response.put("restaurantId", cart.isEmpty() ? null : restaurantId);

        return ResponseEntity.ok(response);
    }

    // SỬA LẠI: phương thức getOrCreateCart dùng CartItemDto
    private List<CartItemDto> getOrCreateCart(HttpSession session) {
        List<CartItemDto> cart = (List<CartItemDto>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

}

