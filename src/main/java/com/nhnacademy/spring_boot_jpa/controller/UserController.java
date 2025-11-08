package com.nhnacademy.spring_boot_jpa.controller;

import com.nhnacademy.spring_boot_jpa.dto.UserCreateCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final RestTemplate restTemplate;

    @Value("${api.account.url}")
    private String accountApiUrl;

    @GetMapping("/signup")
    public String showSignupForm() {
        return "signup"; // templates/signup.html (Thymeleaf 뷰)
    }

    @PostMapping("/signup")
    public String processSignup(@ModelAttribute UserCreateCommand command) {

        // Account-Api의 회원가입 엔드포인트 (예: http://localhost:8081/users)
        String url = accountApiUrl + "/users";
        log.info("Attempting to sign up user at: {}", url);

        try {
            // 4단계: RestTemplate으로 Account-Api 호출
            restTemplate.postForObject(url, command, Void.class); // API가 성공(2xx) 응답을 반환한다고 가정

            // 4단계: 성공 시 로그인 페이지로 리다이렉트
            return "redirect:/login";

        } catch (Exception e) {
            log.error("Signup failed for user: {}", command.getUsername(), e);
            // 실패 시 에러 메시지와 함께 다시 회원가입 폼으로
            return "redirect:/signup?error";
        }
    }
}


