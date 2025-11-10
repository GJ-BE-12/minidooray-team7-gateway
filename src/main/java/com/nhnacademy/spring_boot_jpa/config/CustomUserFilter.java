package com.nhnacademy.spring_boot_jpa.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.spring_boot_jpa.dto.account.LoginRequest;
import com.nhnacademy.spring_boot_jpa.dto.account.UserPrincipal;
import com.nhnacademy.spring_boot_jpa.dto.account.UserResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomUserFilter extends OncePerRequestFilter {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${api.account.url}")
    private String accountApiUrl;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 로그인 요청만 가로채기
        if ("/users/login".equals(request.getServletPath()) && "POST".equalsIgnoreCase(request.getMethod())) {
            String userId = request.getParameter("userId");
            String password = request.getParameter("password");

            LoginRequest loginRequest = new LoginRequest(userId, password);

            log.info("🔐 Login attempt: userId={}, password={}", userId, password);

            try {
                // ✅ Account API에 로그인 요청
                ResponseEntity<UserResponse> apiResponse =
                        restTemplate.postForEntity(accountApiUrl + "/users/login", loginRequest, UserResponse.class);

                log.info("📡 Account API response: {}", apiResponse.getStatusCode());

                if (apiResponse.getStatusCode().is2xxSuccessful() && apiResponse.getBody() != null) {
                    UserResponse userResponse = apiResponse.getBody();

                    // ✅ UserPrincipal 생성
                    UserPrincipal principal = new UserPrincipal(
                            userResponse.getUserId(),
                            userResponse.getUsername(),
                            "", // 비밀번호는 저장하지 않음
                            userResponse.getEmail(),
                            List.of(() -> "ROLE_USER")
                    );

                    // ✅ 인증 객체 생성
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());

                    // ✅ SecurityContext에 등록
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    // ✅ 세션에도 SecurityContext 저장 (로그인 유지)
                    request.getSession().setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

                    log.info("✅ Session stored & authenticated for user: {}", principal.getUsername());

                    // ✅ 프로젝트 목록으로 리다이렉트
                    response.sendRedirect("/projects");
                    return;
                } else {
                    log.warn("❌ Login failed - invalid response");
                    response.sendRedirect("/users/login?error=true");
                    return;
                }

            } catch (Exception e) {
                log.error("🚨 Account API login failed", e);
                response.sendRedirect("/users/login?error=true");
                return;
            }
        }

        // 로그인 이외 요청은 그대로 다음 필터로 전달
        filterChain.doFilter(request, response);
    }
}
