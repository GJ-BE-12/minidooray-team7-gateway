package com.nhnacademy.spring_boot_jpa.service;

import com.nhnacademy.spring_boot_jpa.dto.CommentCreateRequest;
import com.nhnacademy.spring_boot_jpa.dto.ProjectDetailsResponse;
import com.nhnacademy.spring_boot_jpa.dto.ProjectResponse;
import com.nhnacademy.spring_boot_jpa.dto.TaskCreateRequest;
import com.nhnacademy.spring_boot_jpa.dto.TaskDetailsResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

// Task-Api 와 통신을 전담하는 서비스 클래스입니다.
@Slf4j
@Service
@RequiredArgsConstructor
public class TaskApiClient {

    private final RestTemplate restTemplate;

    @Value("${api.task.url}")
    private String taskApiUrl;

    /**
     * (핵심) Task-Api로 보낼 인증 헤더를 생성합니다.
     * @param memberId 1단계에서 정의한 UserPrincipal에서 가져온 로그인 사용자 ID
     * @return HttpHeaders 객체
     */
    private HttpHeaders createAuthHeaders(Long memberId) {
        HttpHeaders headers = new HttpHeaders();
        // Task-Api는 이 헤더를 @RequestHeader("X-MEMBER-ID")로 받아 처리합니다.
        headers.set("X-MEMBER-ID", String.valueOf(memberId));
        return headers;
    }

    // (로그인한 사용자) 프로젝트 목록을 조회
    // Task-Api: GET /projects
    public List<ProjectResponse> getMyProjects(Long memberId) {
        String url = taskApiUrl + "/projects";
        log.info("[TaskApiClient] getMyProjects: {} (User: {})", url, memberId);

        HttpHeaders headers = createAuthHeaders(memberId);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        // 반환 타입이 List<T>인 경우 ParameterizedTypeReference를 사용합니다.
        ParameterizedTypeReference<List<ProjectResponse>> responseType =
                new ParameterizedTypeReference<>() {};

        ResponseEntity<List<ProjectResponse>> response =
                restTemplate.exchange(url, HttpMethod.GET, entity, responseType);

        return response.getBody();
    }

    // (로그인한 사용자) 프로젝트 상세 정보를 조회 (태스크 목록 포함)
    public ProjectDetailsResponse getProjectDetails(Long memberId, Long projectId) {
        String url = taskApiUrl + "/projects/" + projectId;
        log.info("[TaskApiClient] getProjectDetails: {} (User: {})", url, memberId);

        HttpHeaders headers = createAuthHeaders(memberId);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<ProjectDetailsResponse> response =
                restTemplate.exchange(url, HttpMethod.GET, entity, ProjectDetailsResponse.class);

        return response.getBody();
    }

    // (로그인한 사용자) 태스크 상세 정보를 조회합니다. (댓글 목록 포함)
    public TaskDetailsResponse getTaskDetails(Long memberId, Long taskId) {
        String url = taskApiUrl + "/tasks/" + taskId;
        log.info("[TaskApiClient] getTaskDetails: {} (User: {})", url, memberId);

        HttpHeaders headers = createAuthHeaders(memberId);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<TaskDetailsResponse> response =
                restTemplate.exchange(url, HttpMethod.GET, entity, TaskDetailsResponse.class);

        return response.getBody();
    }

    // (로그인한 사용자) 태스크를 생성합니다.
    public void createTask(Long memberId, Long projectId, TaskCreateRequest request) {
        String url = taskApiUrl + "/projects/" + projectId + "/tasks";
        log.info("[TaskApiClient] createTask: {} (User: {})", url, memberId);

        HttpHeaders headers = createAuthHeaders(memberId);
        // 요청 본문(request)과 헤더를 합쳐 HttpEntity를 생성합니다.
        HttpEntity<TaskCreateRequest> entity = new HttpEntity<>(request, headers);

        restTemplate.postForObject(url, entity, Void.class);
    }

    // (로그인한 사용자) 댓글을 생성
    public void createComment(Long memberId, Long taskId, CommentCreateRequest request) {
        String url = taskApiUrl + "/tasks/" + taskId + "/comments";
        log.info("[TaskApiClient] createComment: {} (User: {})", url, memberId);

        HttpHeaders headers = createAuthHeaders(memberId);
        HttpEntity<CommentCreateRequest> entity = new HttpEntity<>(request, headers);

        restTemplate.postForObject(url, entity, Void.class);
    }

}