//package com.nhnacademy.spring_boot_jpa.service;
//
//import com.nhnacademy.spring_boot_jpa.dto.UserAuthResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Collections;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final RestTemplate restTemplate;
//
//    // application.yaml 또는 properties에 Account-Api 주소 설정 필요
//    @Value("${api.account.url}")
//    private String accountApiUrl;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // 3단계: Account-Api 호출 (예: http://localhost:8081/users/{username}/auth)
//        String url = accountApiUrl + "/users/" + username + "/auth";
//        log.info("Attempting to fetch user from: {}", url);
//
//        try {
//            // Account-Api로부터 사용자 인증 정보 수신
//            UserAuthResponse userResponse = restTemplate.getForObject(url, UserAuthResponse.class);
//
//            if (userResponse == null || userResponse.getPassword() == null) {
//                log.warn("User not found or password null from API: {}", username);
//                throw new UsernameNotFoundException("User not found or invalid response: " + username);
//            }
//
//            log.info("User found from API: {}", username);
//
//            // 3단계: Spring Security의 User 객체로 변환하여 반환
//            return new User(
//                    userResponse.getUsername(),
//                    userResponse.getPassword(), // API가 암호화된 비밀번호를 반환해야 함
//                    Collections.singletonList(new SimpleGrantedAuthority(userResponse.getAuthority())) // API가 권한(예: "ROLE_USER")을 반환해야 함
//            );
//
//        } catch (HttpClientErrorException.NotFound e) {
//            log.warn("User not found from API (404): {}", username);
//            throw new UsernameNotFoundException("User not found: " + username, e);
//        } catch (Exception e) {
//            log.error("Error fetching user from API", e);
//            throw new UsernameNotFoundException("Error fetching user: " + username, e);
//        }
//    }
//}