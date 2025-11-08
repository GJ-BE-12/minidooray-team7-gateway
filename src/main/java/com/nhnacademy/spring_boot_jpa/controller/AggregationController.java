//package com.nhnacademy.spring_boot_jpa.controller;
//
//
//import com.nhnacademy.spring_boot_jpa.service.AggregationService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//import reactor.core.publisher.Mono;
//
//@RestController
//@RequiredArgsConstructor
//public class AggregationController {
//
//    private final AggregationService aggregationService;
//
//    /**
//     * 클라이언트가 요청하는 통합 엔드포인트입니다.
//     * (예: GET /views/tasks/1)
//     */
//    @GetMapping("/test")
//    public Mono<String> getAccountView() {
//        // 서비스에서 데이터를 통합하는 로직을 호출합니다.
//        // Gateway는 WebFlux를 사용하므로 Mono<T>를 반환합니다.
//        return aggregationService.test();
//    }
//}