package com.nhnacademy.spring_boot_jpa.service;

import com.nhnacademy.spring_boot_jpa.dto.account.RegisterRequest;
import com.nhnacademy.spring_boot_jpa.dto.UserAuthResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Account Api 연동
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AccountApiClient {
    private final RestTemplate restTemplate;

    @Value("${api.account.url}")
    private String accountApiUrl;

    /**
     * CustomUserDetailsService가 Account-Api를 호출하기 위한 메소드
     * @return UserAuthResponse (memberId, 암호화된 pw, 권한 포함)
     */
    public UserAuthResponse getUserAuthDetails(String username) {
        // (중요) Account-Api에 이 엔드포인트(GET /users/{username}/auth)가 필요합니다.
        String url = this.accountApiUrl + "/users/" + username + "/auth";
        log.info("Fetching auth details from: {}", url);
        try {
            return restTemplate.getForObject(url, UserAuthResponse.class);
        } catch (Exception e) {
            log.warn("Failed to fetch auth details for user: {}", username, e);
            return null; // UserDetailsService가 null을 받아 처리하도록 함
        }
    }

    //회원가입 시도
    public void register(RegisterRequest registerRequest) {
        String url = this.accountApiUrl + "/users/register";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<RegisterRequest> requestEntity = new HttpEntity<>(registerRequest, headers);

        try {
            ResponseEntity<Void> response = restTemplate.postForEntity(url, requestEntity, Void.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                // API가 2xx 이외의 응답을 주면 예외 발생
                throw new RuntimeException("Account API returned error: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            log.error("Failed to register user via Account API", e);
            throw new RuntimeException("Registration failed (API Error)", e);
        }
    }
}