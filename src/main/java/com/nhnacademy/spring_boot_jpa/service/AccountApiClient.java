package com.nhnacademy.spring_boot_jpa.service;


import com.nhnacademy.spring_boot_jpa.dto.account.LoginRequest;
import com.nhnacademy.spring_boot_jpa.dto.account.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.file.attribute.UserPrincipal;

/**
 * Account Api 연동, 사용자가 로그인 시도, 브라우저에서 /login POST 요청
 * AccountController -> AccountApiClient 호출
 */
@Service
public class AccountApiClient {
    private final RestTemplate restTemplate;

    @Value("${account.api.url}")
    private String accountApiUrl;

    public AccountApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // 로그인 시도
    public UserPrincipal login(LoginRequest loginRequest) {
        String url = this.accountApiUrl + "/login";
        ResponseEntity<UserPrincipal> response =
                restTemplate.postForEntity(url, loginRequest, UserPrincipal.class);
        return response.getBody();
    }
    //회원가입 시도
    public void register(RegisterRequest registerRequest) {
        String url = this.accountApiUrl + "/register";
        restTemplate.postForEntity(url, registerRequest, UserPrincipal.class);
    }



}
