
package com.example.case_study_module_4.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @NotBlank(message = "Họ tên không được để trống")
    @Size(max = 100, message = "Họ tên tối đa 100 ký tự")
    @Pattern(regexp = "^[\\p{L}\\s]+$", message = "Họ tên không được chứa số hoặc ký tự đặc biệt")
    private String fullName;


    @Pattern(regexp = "^(|0\\d{9,10}|\\+?\\d{9,15})$", message = "Số điện thoại không hợp lệ")
    private String phone;

    @Size(max = 255, message = "Địa chỉ tối đa 255 ký tự")
    private String address;


}
