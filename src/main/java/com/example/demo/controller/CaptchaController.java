package com.example.demo.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class CaptchaController {

    @GetMapping("/captcha")
    public void captcha(HttpSession session, HttpServletResponse response) throws IOException {

        int width = 150;
        int height = 50;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        g.setFont(new Font("Arial", Font.BOLD, 28));

        String captcha = generateCaptcha(5);
        session.setAttribute("CAPTCHA", captcha);

        g.setColor(Color.BLACK);
        g.drawString(captcha, 30, 35);
        g.dispose();

        response.setContentType("image/png");
        ImageIO.write(image, "png", response.getOutputStream());
    }

    private String generateCaptcha(int length) {
        String chars = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
