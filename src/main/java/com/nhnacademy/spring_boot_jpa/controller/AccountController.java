package com.nhnacademy.spring_boot_jpa.controller;

import com.nhnacademy.spring_boot_jpa.dto.account.LoginRequest;
import com.nhnacademy.spring_boot_jpa.dto.account.RegisterRequest;
import com.nhnacademy.spring_boot_jpa.service.AccountApiClient;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.nio.file.attribute.UserPrincipal;

/**
 * 로그인 , 회원가입 뷰
 */
@Controller
public class AccountController {

    private final AccountApiClient accountApiClient;

    public AccountController(AccountApiClient accountApiClient) {
        this.accountApiClient = accountApiClient;
    }

    @GetMapping("/login")
    public String loginForm(){
        return "login";
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequest loginRequest, HttpSession session) {
        UserPrincipal user = accountApiClient.login(loginRequest);
        if(user ==null){
            return "redirect:/login?error";
        }
        session.setAttribute("user", user); //세션에 인증 정보 저장
        return "redirect:/projects";
    }

    //회원가입 폼
    @GetMapping("/register")
    public String registerForm(){
        return "register";
    }

    //회원가입처리
    @PostMapping("/register")
    public String register(@ModelAttribute RegisterRequest registerRequest) {
        accountApiClient.register(registerRequest);
        return "redirect:/login";
    }
    //로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
