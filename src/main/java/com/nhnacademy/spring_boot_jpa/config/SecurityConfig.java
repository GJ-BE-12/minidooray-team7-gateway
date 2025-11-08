package com.nhnacademy.spring_boot_jpa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean // SecurityFilterChain을 Bean으로 등록
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // CSRF 설정 (람다식으로 변경)
                .authorizeHttpRequests(auth -> auth // 인증 설정
                        .requestMatchers("/login", "/register","/css/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form //  폼 로그인 설정
                        .loginPage("/login")
                        .defaultSuccessUrl("/projects", true)
                )
                .logout(logout -> logout // 로그아웃 설정
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                );
        return http.build(); // build() 호출
    }

}
