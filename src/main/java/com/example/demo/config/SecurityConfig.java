package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.filter.CaptchaValidationFilter;

@Configuration
public class SecurityConfig {

    private final CaptchaValidationFilter captchaFilter;

    // ✅ CORRECT constructor injection (NO @Bean here)
    public SecurityConfig(CaptchaValidationFilter captchaFilter) {
        this.captchaFilter = captchaFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/css/**",
                    "/js/**",
                    "/images/**",
                    "/login",
                    "/register",
                    "/forgot-password",
                    "/reset-password/**",
                    "/captcha"
                ).permitAll()
                .anyRequest().authenticated()
            )

            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )

            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
            );

        // 🚨 THIS LINE MAKES CAPTCHA WORK
        http.addFilterBefore(
            captchaFilter,
            UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }
}
