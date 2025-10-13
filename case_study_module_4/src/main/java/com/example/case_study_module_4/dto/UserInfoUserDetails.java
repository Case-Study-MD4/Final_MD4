package com.example.case_study_module_4.dto;

import com.example.case_study_module_4.entity.Account;
import com.example.case_study_module_4.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class UserInfoUserDetails implements UserDetails {

    private final Long accountId;
    private final String username;
    private final String password;
    private final List<GrantedAuthority> authorities;

    // optional profile
    private final Long userId;
    private final String fullName;

    public UserInfoUserDetails(Account account, User profile) {
        Objects.requireNonNull(account, "account must not be null");
        this.accountId = account.getId();
        this.username = account.getUsername();
        this.password = account.getPassword();

        String roleName = "USER";
        if (account.getRole() != null && account.getRole().getRoleName() != null) {
            roleName = account.getRole().getRoleName().trim().toUpperCase();
        }

        this.authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + roleName));


        if (profile != null) {
            this.userId = profile.getId();
            this.fullName = profile.getFullname();
        } else {
            this.userId = null;
            this.fullName = null;
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
