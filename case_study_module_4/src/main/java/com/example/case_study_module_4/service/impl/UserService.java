package com.example.case_study_module_4.service.impl;


import com.example.case_study_module_4.dto.UserDto;
import com.example.case_study_module_4.entity.User;
import com.example.case_study_module_4.repository.IUserRepository;
import com.example.case_study_module_4.service.IUserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository repository;


    @Override
    public User getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy người dùng id=" + id));
    }

    @Override
    public void updateProfile(Integer id, UserDto dto) {
        User user = getById(id);
        user.setFullname(dto.getFullname());
        user.setPhone(dto.getPhone());
        user.setAddress(dto.getAddress());

    }
}