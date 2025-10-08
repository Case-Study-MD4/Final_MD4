package com.example.case_study_module_4.controller;

import com.example.case_study_module_4.entity.Order;
import com.example.case_study_module_4.service.impl.OrderService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/history")
    public String getOrderHistory(Model model) {
        // Sau này thay bằng SecurityContextHolder.getContext().getAuthentication()
        Long currentUserId = 1L;
        List<Order> orders = orderService.getOrdersByUser(currentUserId);

        model.addAttribute("orders", orders);
        return "orders/history";
    }
    @GetMapping("/success/{id}")
    public String orderSuccess(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        return "orders/success";
    }


}

