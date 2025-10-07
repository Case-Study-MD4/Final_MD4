package com.example.case_study_module_4.service.impl;

import com.example.case_study_module_4.dto.UserInfoUserDetails;
import com.example.case_study_module_4.entity.Account;
import com.example.case_study_module_4.entity.User;
import com.example.case_study_module_4.repository.IAccountRepository;
import com.example.case_study_module_4.repository.IUserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoDetailService implements UserDetailsService {

    private final IAccountRepository accountRepository;
    private final IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account acc = accountRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy tài khoản: " + username));

        // lấy profile (nếu có)
        User profile = userRepository.findByAccount_Id(acc.getId()).orElse(null);

        return new UserInfoUserDetails(acc, profile);
    }


}
