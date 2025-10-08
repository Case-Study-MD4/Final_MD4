package com.example.case_study_module_4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class AuthController {
    @GetMapping("/login")
    public String login(Principal principal) {
        // nếu đã đăng nhập thì cho ra thẳng trang sau login, tránh /login lặp
        if (principal != null) return "redirect:/orders/history";
        return "user/login"; // resources/templates/login.html
    }
}