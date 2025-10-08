package com.example.case_study_module_4.controller;

import com.example.case_study_module_4.entity.Order;
import com.example.case_study_module_4.entity.User;
import com.example.case_study_module_4.service.impl.OrderService;
import com.example.case_study_module_4.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    // Lấy lịch sử đơn hàng của user đang đăng nhập
    @GetMapping("/history")
    public String getOrderHistory(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        // Lấy user từ username
        User currentUser = userService.findByUsername(username);
        if (currentUser == null) {
            return "redirect:/login";
        }

        // Lấy danh sách đơn hàng theo user
        List<Order> orders = orderService.getOrdersByUser(currentUser.getId());

        model.addAttribute("orders", orders);
        return "orders/history";
    }

    // Trang đặt hàng thành công
    @GetMapping("/success/{id}")
    public String orderSuccess(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id);
        if (order == null) {
            return "redirect:/menu";
        }

        model.addAttribute("order", order);
        return "orders/success";
    }
}
