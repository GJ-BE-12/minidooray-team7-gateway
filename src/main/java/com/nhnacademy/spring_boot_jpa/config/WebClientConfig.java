package com.nhnacademy.spring_boot_jpa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        // Gateway에서 사용할 WebClient Bean을 생성합니다.
        // 여기서는 기본 URL을 설정하지 않고, 호출 시점에 명시합니다 (유연성 확보).
        return WebClient.builder().build();
    }
}