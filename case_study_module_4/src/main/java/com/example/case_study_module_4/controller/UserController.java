//package com.example.case_study_module_4.controller;
//
//import com.example.case_study_module_4.dto.UserDto;
//import com.example.case_study_module_4.entity.User;
//import com.example.case_study_module_4.exception.UserNotFoundException;
//import com.example.case_study_module_4.service.IUserService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//@Controller
//@RequestMapping("/profile/users")
//@RequiredArgsConstructor
//public class UserController {
//
//    private final IUserService userService;
//
//    // ========= Xem chi tiết =========
//    @GetMapping("/{id}")
//    public String viewDetail(@PathVariable Long id, Model model) {
//        User user = java.util.Optional.ofNullable(userService.getById(id))
//                .orElseThrow(() -> new UserNotFoundException(id));
//        model.addAttribute("user", user);
//        return "users/detail"; // templates/users/detail.html
//    }
//
//    // ========= Form sửa =========
//    @GetMapping("/{id}/edit")
//    public String showEditForm(@PathVariable Long id, Model model) {
//        User user = java.util.Optional.ofNullable(userService.getById(id))
//                .orElseThrow(() -> new UserNotFoundException(id));
//
//        UserDto dto = new UserDto();
//        dto.setFullName(user.getFullname());
//        dto.setPhone(user.getPhone());
//        dto.setAddress(user.getAddress());
//
//        model.addAttribute("userId", id);
//        model.addAttribute("form", dto);
//        return "users/edit"; // templates/users/edit.html
//    }
//
//    // ========= Submit sửa =========
//    @PostMapping("/{id}/edit")
//    public String update(@PathVariable Long id,
//                         @Valid @ModelAttribute("form") UserDto dto,
//                         BindingResult binding,
//                         Model model,
//                         RedirectAttributes ra) {
//        // đảm bảo tồn tại trước khi cập nhật
//        java.util.Optional.ofNullable(userService.getById(id))
//                .orElseThrow(() -> new UserNotFoundException(id));
//
//        if (binding.hasErrors()) {
//            model.addAttribute("userId", id);
//            return "users/edit";
//        }
//
//        userService.updateProfile(id, dto);
//        ra.addFlashAttribute("success", "Cập nhật người dùng thành công!");
//        return "redirect:/profile/users/" + id;
//    }
//
//    // ========= Xoá =========
//    @PostMapping("/{id}/delete")
//    public String delete(@PathVariable Long id, RedirectAttributes ra) {
//        // ném lỗi nếu không tồn tại
//        java.util.Optional.ofNullable(userService.getById(id))
//                .orElseThrow(() -> new UserNotFoundException(id));
//
//        userService.deleteById(id);
//        ra.addFlashAttribute("success", "Đã xoá người dùng #" + id);
//        return "redirect:/users"; // đổi sang trang danh sách mà ông đang dùng
//    }
//}
