package com.example.case_study_module_4.config;

import com.example.case_study_module_4.service.impl.UserInfoDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final UserInfoDetailService userInfoDetailService;

    public SecurityConfig(UserInfoDetailService userInfoDetailService) {
        this.userInfoDetailService = userInfoDetailService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // hỗ trợ {bcrypt}, {noop}, ...
        return org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(PasswordEncoder encoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userInfoDetailService);
        provider.setPasswordEncoder(encoder);
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // ✅ cho phép static files (tránh loop do 302 liên tục)
                        .requestMatchers("/css/**","/js/**","/images/**","/webjars/**","home","menu").permitAll()

                        // ✅ trang public
                        .requestMatchers("/login", "/logoutSuccessful", "/403","/home").permitAll()

                        // ✅ phân quyền cụ thể
                        .requestMatchers("/orders/create").hasRole("ADMIN")
                        .requestMatchers("/orders/history").authenticated()
                        .requestMatchers("/logout").authenticated()

                        // ✅ mọi thứ khác phải đăng nhập (tránh rơi vào redirect bất ngờ)
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .failureUrl("/login?error=true")

                        .successHandler((request, response, authentication) -> {
                            var authorities = authentication.getAuthorities();
                            String redirectUrl = "/home";

                            if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_RESTAURANT_OWNER"))) {
                                redirectUrl = "restaurant/dashboard";
                            }

                            response.sendRedirect(redirectUrl);
                        })
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/home")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .exceptionHandling(ex -> ex.accessDeniedPage("/403"))
                .requestCache(RequestCacheConfigurer::disable)
                // ✅ đăng ký provider rõ ràng
                .authenticationProvider(authenticationProvider(passwordEncoder()));


        return httpSecurity.build();
    }
}
