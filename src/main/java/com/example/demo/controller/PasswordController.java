package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
 
import com.example.demo.model.User;
import com.example.demo.model.VerificationToken;
import com.example.demo.repository.TokenRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.EmailService;


@Controller
public class PasswordController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TokenRepository tokenRepo;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder encoder;

    @GetMapping("/forgot-password")
    public String forgotPage() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String forgot(@RequestParam String email) {

        User user = userRepo.findByEmail(email).orElseThrow();

        String token = UUID.randomUUID().toString();

        VerificationToken vt = new VerificationToken();
        vt.setToken(token);
        vt.setUser(user);
        vt.setExpiryDate(LocalDateTime.now().plusMinutes(15));
        tokenRepo.save(vt);

        emailService.send(
            email,
            "Reset Password",
            "Click link: http://localhost:8080/reset-password/" + token
        );

        return "forgot-success";
    }

    @GetMapping("/reset-password/{token}")
    public String resetForm(@PathVariable String token, Model model) {
        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String reset(@RequestParam String token,
                        @RequestParam String password) {

        VerificationToken vt = tokenRepo.findByToken(token).orElseThrow();
        User user = vt.getUser();
        user.setPassword(encoder.encode(password));
        userRepo.save(user);

        tokenRepo.delete(vt);
        return "redirect:/login";
    }
}
