package com.example.case_study_module_4.controller;

import com.example.case_study_module_4.entity.Order;
import com.example.case_study_module_4.service.impl.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/history")
    public String getOrderHistory(Model model) {
        // Sau này thay bằng SecurityContextHolder.getContext().getAuthentication()
        Long currentUserId = 1L; // tạm giả định user_id = 1
        List<Order> orders = orderService.getOrdersByUser(currentUserId);

        model.addAttribute("orders", orders);
        return "orders/history";
    }
}

