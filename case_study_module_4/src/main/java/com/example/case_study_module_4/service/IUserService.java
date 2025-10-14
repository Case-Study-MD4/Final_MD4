package com.example.case_study_module_4.service;

import com.example.case_study_module_4.dto.UserDto;
import com.example.case_study_module_4.entity.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    User getById(Long id);
    void updateProfile(Long id, UserDto dto);

    User findByUsername(String username);

    List<User> findAll();

    void deleteById(Long id);

    Optional<User> findById(Long id);


    User getByIdOrThrow(Long id);        // <-- dùng cho controller




}
