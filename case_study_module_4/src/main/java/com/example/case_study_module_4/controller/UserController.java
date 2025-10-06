package com.example.case_study_module_4.controller;

import com.example.case_study_module_4.dto.UserDto;
import com.example.case_study_module_4.entity.User;
import com.example.case_study_module_4.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;



    private Long currentUserId() {
        return 1L;
    }

    @GetMapping("/edit")
    public String showEditForm(Model model) {
        User user = userService.getById(currentUserId());
        UserDto dto = new UserDto();
        dto.setFullname(user.getFullname());
        dto.setPhone(user.getPhone());
        dto.setAddress(user.getAddress());
        model.addAttribute("profile", dto);
        return "profile/edit";
    }

    @PostMapping("/edit")
    public String updateProfile(@Valid @ModelAttribute("profile") UserDto dto,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            return "profile/edit";
        }
        userService.updateProfile(currentUserId(), dto);
        model.addAttribute("success", "Cập nhật thông tin thành công!");
        return "profile/edit";
    }
}
