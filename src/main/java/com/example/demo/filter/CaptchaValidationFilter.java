package com.example.demo.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class CaptchaValidationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        if ("/login".equals(request.getServletPath())
                && "POST".equalsIgnoreCase(request.getMethod())) {

            HttpSession session = request.getSession(false);

            if (session == null) {
                response.sendRedirect("/login?error=captcha");
                return;
            }

            String sessionCaptcha = (String) session.getAttribute("CAPTCHA");
            String userCaptcha = request.getParameter("captcha");

            if (sessionCaptcha == null
                    || userCaptcha == null
                    || !sessionCaptcha.equalsIgnoreCase(userCaptcha)) {

                response.sendRedirect("/login?error=captcha");
                return;
            }

            // Prevent reuse (safe now)
            session.removeAttribute("CAPTCHA");
        }

        filterChain.doFilter(request, response);
    }
}

