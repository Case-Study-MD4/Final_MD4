package com.example.case_study_module_4.service.impl;

import com.example.case_study_module_4.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final IUserRepository userRepository;

    public Long getCustomerCount() {
        return userRepository.countAllCustomers();
    }

    public Long getOwnerCount() {
        return userRepository.countAllOwners();
    }
}
