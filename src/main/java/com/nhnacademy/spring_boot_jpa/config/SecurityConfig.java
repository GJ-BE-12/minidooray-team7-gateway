//package com.nhnacademy.spring_boot_jpa.config;
//
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
////
////@Configuration
////@EnableWebSecurity
////@RequiredArgsConstructor
//public class SecurityConfig {
//
//    // UserDetailsService 빈(CustomUserDetailsService)이 자동으로 사용됩니다.
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                // CSRF 비활성화 (API 호출 및 폼 처리를 위해)
//                .csrf(csrf -> csrf.disable())
//
//                .authorizeHttpRequests(auth -> auth
//                        // 4단계: 회원가입(/signup) 경로는 누구나 접근 허용
//                        .requestMatchers("/login", "/signup", "/css/**", "/js/**").permitAll()
//                        .anyRequest().authenticated() // 나머지는 인증 필요
//                )
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .defaultSuccessUrl("/projectList", true) // 로그인 성공 시
//                        .permitAll()
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/login?logout")
//                        // @EnableRedisHttpSession이 세션 무효화 및 쿠키 삭제를 처리합니다.
//                        .invalidateHttpSession(true)
//                        .permitAll()
//                );
//        return http.build();
//    }
//
//    /**
//     * 3단계: PasswordEncoder Bean 등록 (필수)
//     */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}