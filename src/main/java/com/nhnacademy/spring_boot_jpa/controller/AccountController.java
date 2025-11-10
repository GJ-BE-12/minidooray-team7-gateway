package com.nhnacademy.spring_boot_jpa.controller;

import com.nhnacademy.spring_boot_jpa.dto.account.LoginRequest;
import com.nhnacademy.spring_boot_jpa.dto.account.RegisterRequest;
import com.nhnacademy.spring_boot_jpa.dto.account.UserResponse;
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

    //로그인 처리
    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequest loginRequest,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {
        try {
            // TODO: accountApiClient.login() 메서드가 반환하는 사용자 정보를 세션에 저장
            // 현재는 단순히 로그인 성공 여부만 확인하고, 성공 시 사용자 ID를 세션에 저장한다고 가정
            UserResponse userResponse = accountApiClient.login(loginRequest); // UserResponse 객체 반환
            session.setAttribute("userId", String.valueOf(userResponse.getUserId())); // 세션에 사용자 ID (String으로 변환) 저장
            session.setAttribute("username", userResponse.getUsername()); // 세션에 사용자 이름 저장
            log.info("User logged in: {} (ID: {})", userResponse.getUsername(), userResponse.getUserId());
            return "redirect:/projects"; // 로그인 성공 시 리다이렉트할 페이지

        } catch (Exception e) {
            log.warn("Login failed for user: {}", loginRequest.getUserId(), e);
            redirectAttributes.addFlashAttribute("error", "로그인에 실패했습니다. 아이디 또는 비밀번호를 확인해주세요.");
            return "redirect:/users/login";
        }
    }
}
