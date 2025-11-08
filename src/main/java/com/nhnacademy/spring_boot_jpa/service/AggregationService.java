package com.nhnacademy.spring_boot_jpa.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AggregationService {

    private final WebClient webClient;

    // 🚨 TaskAPI와 AccountAPI의 IP와 포트 (application.yml에 설정된 실제 주소)
    // 실제 운영 환경에서는 Service Discovery (Eureka 등)를 사용합니다.
//    private static final String TASK_API_URL = "http://192.168.1.50:8082";
    private static final String ACCOUNT_API_URL = "http://220.67.223.66:8082/teasks_api"; // 예시

    /**
     * 특정 사용자의 Task 상세 정보와 사용자 정보를 통합하여 조회합니다.
     * WebClient는 비동기 방식인 Mono<T>를 반환합니다.
     */
//    public Mono<TaskDetail> getCombinedTaskDetail(Long taskId) {
//        // 1. Task API 호출 (Task 상세 정보)
//        Mono<TaskDetail> taskMono = webClient.get()
//                .uri(TASK_API_URL + "/tasks/{taskId}", taskId)
//                .retrieve()
//                .bodyToMono(TaskDetail.class);
//
//        // 2. Account API 호출 (Task의 user_id를 이용해 사용자 정보 조회)
//        // 이 부분은 실제 TaskDetail 모델에 user_id가 포함되어 있다는 전제 하에 복잡해질 수 있습니다.
//        // 여기서는 단순화를 위해 생략하고, 나중에 필요할 때 .zipWith()을 사용해 구현합니다.
//
//        return taskMono;
//    }

    // 사용자 정보를 가져오는 단순 호출 예시
    public Mono<String> test() {
        return webClient.get()
                .uri(ACCOUNT_API_URL + "/test")
                .retrieve()
                .bodyToMono(String.class);
    }
}