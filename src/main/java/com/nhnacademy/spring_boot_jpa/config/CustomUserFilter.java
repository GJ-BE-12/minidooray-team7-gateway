//package com.nhnacademy.spring_boot_jpa.config;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import com.nhnacademy.spring_boot_jpa.dto.account.LoginRequest;
//import com.nhnacademy.spring_boot_jpa.dto.account.UserPrincipal;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Component
//@RequiredArgsConstructor
//public class CustomUserFilter extends OncePerRequestFilter {
//
//    private final RestTemplate restTemplate;
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @Value("${api.account.url}")
//    private String accountApiUrl;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain)
//            throws ServletException, IOException {
//
//        // 로그인 요청만 가로채기
//        if ("/login".equals(request.getServletPath()) && "POST".equalsIgnoreCase(request.getMethod())) {
//            String id = request.getParameter("id");
//            String password = request.getParameter("password");
//
//            LoginRequest loginRequest = new LoginRequest(id, password);
//
//            try {
//                ResponseEntity<UserPrincipal> apiResponse =
//                        restTemplate.postForEntity(accountApiUrl + "/login", loginRequest, UserPrincipal.class);
//
//                if (apiResponse.getStatusCode().is2xxSuccessful() && apiResponse.getBody() != null) {
//                    request.getSession().setAttribute("user", apiResponse.getBody());
//                    response.sendRedirect("/projects");
//                    return;
//                } else {
//                    response.sendRedirect("/login?error=true");
//                    return;
//                }
//
//            } catch (Exception e) {
//                response.sendRedirect("/login?error=true");
//                return;
//            }
//        }
//
//        // 로그인 이외 요청은 그대로 다음 필터로 전달
//        filterChain.doFilter(request, response);
//    }
//}
