package com.example.case_study_module_4.controller;

import com.example.case_study_module_4.service.impl.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @GetMapping("/send-email")
    public String showEmailForm() {
        return "email/email_form";
    }

    @PostMapping("/send-email")
    public String sendEmail(@RequestParam("to") String to,
                            @RequestParam("subject") String subject,
                            @RequestParam("content") String content,
                            Model model) {
        emailService.sendMail(to, subject, content);
        model.addAttribute("message", "✅ Gửi mail thành công đến " + to);
        return "email/email_form";
    }
}
