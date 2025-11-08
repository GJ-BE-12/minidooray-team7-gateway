package com.nhnacademy.spring_boot_jpa.controller;

import com.nhnacademy.spring_boot_jpa.dto.account.LoginRequest;
import com.nhnacademy.spring_boot_jpa.dto.account.RegisterRequest;
import com.nhnacademy.spring_boot_jpa.service.AccountApiClient;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.attribute.UserPrincipal;

/**
 * 로그인 , 회원가입 뷰
 */
@Slf4j
@Controller
@RequestMapping("/users")
public class AccountController {

    private final AccountApiClient accountApiClient;

    public AccountController(AccountApiClient accountApiClient) {
        this.accountApiClient = accountApiClient;
    }

    @GetMapping("/login")
    public String loginForm(){
        return "login";
    }

    //회원가입 폼
    @GetMapping("/register")
    public String registerForm(){
        return "register";
    }

    //회원가입처리
    @PostMapping("/register")
    public String register(@ModelAttribute RegisterRequest registerRequest,
                           RedirectAttributes redirectAttributes) { // 👈 RedirectAttributes 추가
        try {
            accountApiClient.register(registerRequest);
            // (선택) 회원가입 성공 시 메시지 전달
            redirectAttributes.addFlashAttribute("success", "회원가입이 완료되었습니다. 로그인해주세요.");
            return "redirect:/users/login";

        } catch (Exception e) {
            log.warn("Registration failed for user: {}", registerRequest.getUsername(), e);
            // ⬇️ 실패 시 에러 메시지를 Flash Attribute로 전달
            redirectAttributes.addFlashAttribute("error", "회원가입에 실패했습니다. (아이디 또는 이메일이 중복될 수 있습니다)");
            // ⬇️ 실패 시 로그인 페이지가 아닌 회원가입 페이지로 다시 리다이렉트
            return "redirect:/users/register";
        }
    }
}
