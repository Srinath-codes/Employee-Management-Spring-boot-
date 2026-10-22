package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.google.code.kaptcha.impl.DefaultKaptcha;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    @SuppressWarnings("unused")
    private DefaultKaptcha captchaProducer;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
        return "redirect:/login";
    }

    public PasswordEncoder getEncoder() {
        return encoder;
    }

    public void setEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    
}
