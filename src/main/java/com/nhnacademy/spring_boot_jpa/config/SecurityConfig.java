package com.nhnacademy.spring_boot_jpa.config;

import com.nhnacademy.spring_boot_jpa.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

//    private final CustomUserDetailsService customUserDetailsService;
    private final CustomUserFilter customUserFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**").permitAll()
                        .requestMatchers("/users/login", "/users/register", "/users/{username}/auth").permitAll()
                        .requestMatchers("/users/login", "/users/register", "/users/{username}/auth").permitAll()
                        .anyRequest().authenticated()
                )
//                .formLogin(form -> form
//                        .loginPage("/users/login") // 로그인 폼 GET URL
//                        .loginProcessingUrl("/users/login") // 로그인 폼 POST URL (Security가 처리)
//                        .defaultSuccessUrl("/projects", true)
//                        .permitAll()
                // Security 기본 formLogin 비활성화
                .formLogin(AbstractHttpConfigurer::disable)

                .logout(logout -> logout
                        .logoutUrl("/users/logout")
                        .logoutSuccessUrl("/users/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
                // ✅ CustomUserFilter를 Security 체인에 추가
                .addFilterBefore(customUserFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * 비밀번호 암호화기 (BCrypt)
     * Account-Api는 회원가입 시 반드시 이 방식과 동일하게 암호화해야 합니다.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    /**
//     * 인증 공급자(AuthenticationProvider) 설정
//     * Spring Security가 5번의 CustomUserDetailsService를 사용하도록 연결합니다.
//     */
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(customUserDetailsService); // 5번 서비스 설정
//        provider.setPasswordEncoder(passwordEncoder()); // 암호화기 설정
//        return provider;
//    }
}