package com.nhnacademy.spring_boot_jpa.config;

import org.springframework.web.servlet.config.annotation.*;

public class WebConfig implements WebMvcConfigurer {

    /**
     * 정적 리소스 매핑 설정
     * /css/, /js/, /images/ 경로의 리소스를 static 폴더로 연결
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/");
    }
    /**
     * CORS 설정 (필요 시)
     * API 서버(Account API, Task API 등)와 통신할 때 사용
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8081", "http://localhost:8082") // Account API, Task API 서버 주소
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true);
    }
    /**
     * 인터셉터 등록 (로그인 세션 확인용)
     * 예: 로그인된 사용자만 접근 가능한 페이지 제한
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionCheckInterceptor())
                .addPathPatterns("/**") // 모든 요청에 대해 확인
                .excludePathPatterns("/login", "/register", "/css/**", "/js/**", "/images/**"); // 예외 경로
    }

    /**
     *  뷰 컨트롤러 (간단한 경로 → 템플릿 매핑)
     * 컨트롤러 없이 바로 페이지 연결 가능
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/register").setViewName("register");
    }

}
