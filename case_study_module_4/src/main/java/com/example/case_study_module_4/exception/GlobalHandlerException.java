package com.example.case_study_module_4.exception;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "com.example.case_study_module_4")
public class GlobalHandlerException {

    @ExceptionHandler({UserNotFoundException.class, RestaurantNotFoundException.class, FoodNotFoundException.class})
    public String handleNotFound(Exception ex, Model model, HttpServletResponse resp) {
        resp.setStatus(HttpStatus.NOT_FOUND.value());
        model.addAttribute("status", 404);
        model.addAttribute("err", "Không tìm thấy");

        String msg = "Không tìm thấy dữ liệu.";
        if (ex instanceof RestaurantNotFoundException) msg = "Không tìm thấy nhà hàng.";
        if (ex instanceof FoodNotFoundException)       msg = "Món ăn không tồn tại hoặc đã bị xoá.";
        if (ex instanceof UserNotFoundException)        msg = "Không tìm thấy người dùng.";

        model.addAttribute("errMess", msg);
        return "error";
    }


    // Fallback: lỗi khác -> 500
    @ExceptionHandler(Exception.class)
    public String handleOthers(Exception ex, Model model, HttpServletResponse resp) {
        // nếu model đã có status 404 thì giữ nguyên, đừng đổi sang 500
        Object current = model.getAttribute("status");
        if (current != null && "404".equals(String.valueOf(current))) {
            return "error";
        }
        resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        model.addAttribute("status", 500);
        model.addAttribute("err", "Lỗi hệ thống");
        model.addAttribute("errMess", "Đã xảy ra lỗi không mong muốn. Vui lòng thử lại sau.");
        return "error";
    }

}

