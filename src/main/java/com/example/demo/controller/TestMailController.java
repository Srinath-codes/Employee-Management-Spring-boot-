package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.EmailService;

@RestController
public class TestMailController {

    @Autowired
    private EmailService emailService;  // <-- must match package

    @GetMapping("/test-mail")
    public String testMail() {
        emailService.send(
                "yourgmail@gmail.com",  
                "Test Email from Spring Boot",
                "Hello! This is a test email using App Password."
        );
        return "Mail Sent!";
    }
}
