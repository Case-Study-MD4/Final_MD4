package com.example.case_study_module_4.service;

import com.example.case_study_module_4.dto.UserDto;
import com.example.case_study_module_4.entity.User;

public interface IUserService {
    User getById(Long id);
    void updateProfile(Long id, UserDto dto);

    User findByUsername(String username);
}
