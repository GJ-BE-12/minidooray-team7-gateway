package com.nhnacademy.spring_boot_jpa.service;

import com.nhnacademy.spring_boot_jpa.dto.comment.CommentCreateRequest;
import com.nhnacademy.spring_boot_jpa.dto.comment.CommentUpdateRequest;
import com.nhnacademy.spring_boot_jpa.dto.milestone.MilestoneCreateRequest;
import com.nhnacademy.spring_boot_jpa.dto.milestone.MilestoneResponse;
import com.nhnacademy.spring_boot_jpa.dto.milestone.MilestoneUpdateRequest;
import com.nhnacademy.spring_boot_jpa.dto.project.*;
import com.nhnacademy.spring_boot_jpa.dto.tag.TagCreateRequest;
import com.nhnacademy.spring_boot_jpa.dto.tag.TagResponse;
import com.nhnacademy.spring_boot_jpa.dto.tag.TagUpdateRequest;
import com.nhnacademy.spring_boot_jpa.dto.task.TaskCreateRequest;
import com.nhnacademy.spring_boot_jpa.dto.task.TaskDetailsResponse;

import com.nhnacademy.spring_boot_jpa.dto.task.TaskUpdateRequest;
import com.nhnacademy.spring_boot_jpa.service.taskapi.*;
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
public class TaskApiClient implements ProjectService, TaskService, CommentService, TagService, MilestoneService {

    private final RestTemplate restTemplate;

    @Value("${api.task.url}")
    private String taskApiUrl;

    /**
     * Task-Api로 보낼 인증 헤더를 생성합니다.
     */
    private HttpHeaders createAuthHeaders(String userId) {
        HttpHeaders headers = new HttpHeaders();
        // Task-Api는 이 헤더를 @RequestHeader("X-MEMBER-ID")로 받아 처리합니다.
        headers.set("X-MEMBER-ID", String.valueOf(userId));
        return headers;
    }

    // ------------------------------------------------------------------------------------

    // (로그인한 사용자) 프로젝트 목록을 조회
    // Task-Api: GET /projects
    @Override
    public List<ProjectResponse> getMyProjects(String userId) {
        String url = taskApiUrl + "/projects";
        log.info("[TaskApiClient] getMyProjects: {} (User: {})", url, userId);

        HttpHeaders headers = createAuthHeaders(userId);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        // 반환 타입이 List<T>인 경우 ParameterizedTypeReference를 사용합니다.
        ParameterizedTypeReference<List<ProjectResponse>> responseType =
                new ParameterizedTypeReference<>() {};

        ResponseEntity<List<ProjectResponse>> response =
                restTemplate.exchange(url, HttpMethod.GET, entity, responseType);

        return response.getBody();
    }

    // (로그인한 사용자) 프로젝트 상세 정보를 조회 (태스크 목록 포함)
    @Override
    public ProjectDetailsResponse getProjectDetails(String userId, Long projectId) {
        String url = taskApiUrl + "/projects/" + projectId;
        log.info("[TaskApiClient] getProjectDetails: {} (User: {})", url, userId);

        HttpHeaders headers = createAuthHeaders(userId);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<ProjectDetailsResponse> response =
                restTemplate.exchange(url, HttpMethod.GET, entity, ProjectDetailsResponse.class);

        return response.getBody();
    }

    // 프로젝트 생성
    @Override
    public void createProject(ProjectCreateRequest request) {
        String url = taskApiUrl + "/projects";
        HttpEntity<ProjectCreateRequest> entity = new HttpEntity<>(request);
        restTemplate.postForObject(url, entity, Void.class);
    }

    // 프로젝트 수정
//    @Override
//    public void updateProject(String userId, Long projectId, ProjectUpdateRequest request) {
//        String url = taskApiUrl + "/projects/" + projectId;
//        HttpHeaders headers = createAuthHeaders(userId);
//        HttpEntity<ProjectUpdateRequest> entity = new HttpEntity<>(request, headers);
//        restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);
//    }

//    @Override
//    public void deleteProject(String userId, Long projectId) {
//        String url = taskApiUrl + "/projects/" + projectId;
//        HttpHeaders headers = createAuthHeaders(userId);
//        HttpEntity<Void> entity = new HttpEntity<>(headers);
//        restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
//    }

    // ------------------------------------------------------------------------------------

    // 프로젝트 멤버 목록 조회
    @Override
    public List<ProjectMemberResponse> getProjectMembers(String userId, Long projectId) {
        String url = taskApiUrl + "/projects/" + projectId + "/members";
        HttpHeaders headers = createAuthHeaders(userId);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ParameterizedTypeReference<List<ProjectMemberResponse>> responseType = new ParameterizedTypeReference<>() {};
        ResponseEntity<List<ProjectMemberResponse>> response = restTemplate.exchange(url, HttpMethod.GET, entity, responseType);
        return response.getBody();
    }

    // 프로젝트 멤버 추가 (API 명세에 따라 수정 필요)
    @Override
    public void addProjectMember(String userId, Long projectId, String username) {
        String url = taskApiUrl + "/projects/" + projectId + "/members?username=" + username;
        HttpHeaders headers = createAuthHeaders(userId);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        restTemplate.postForObject(url, entity, Void.class);
    }

    // 프로젝트 멤버 삭제
    @Override
    public void deleteProjectMember(String userId, Long projectId, Long userIdToRemove) {
        String url = taskApiUrl + "/projects/" + projectId + "/members/" + userIdToRemove;
        HttpHeaders headers = createAuthHeaders(userId);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
    }

    // ------------------------------------------------------------------------------------

    // (로그인한 사용자) 태스크 상세 정보를 조회합니다. (댓글 목록 포함)
    @Override
    public TaskDetailsResponse getTaskDetails(String userId, Long taskId) {
        String url = taskApiUrl + "/tasks/" + taskId;
        log.info("[TaskApiClient] getTaskDetails: {} (User: {})", url, userId);

        HttpHeaders headers = createAuthHeaders(userId);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<TaskDetailsResponse> response =
                restTemplate.exchange(url, HttpMethod.GET, entity, TaskDetailsResponse.class);

        return response.getBody();
    }

    // (로그인한 사용자) 태스크를 생성합니다.
    @Override
    public void createTask(String userId, Long projectId, TaskCreateRequest request) {
        String url = taskApiUrl + "/projects/" + projectId + "/tasks";
        log.info("[TaskApiClient] createTask: {} (User: {})", url, userId);

        HttpHeaders headers = createAuthHeaders(userId);
        HttpEntity<TaskCreateRequest> entity = new HttpEntity<>(request, headers);

        restTemplate.postForObject(url, entity, Void.class);
    }

    // 태스크 수정
    @Override
    public void updateTask(String userId, Long taskId, TaskUpdateRequest request) {
        String url = taskApiUrl + "/tasks/" + taskId;
        HttpHeaders headers = createAuthHeaders(userId);
        HttpEntity<TaskUpdateRequest> entity = new HttpEntity<>(request, headers);
        restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);
    }

    // 태스크 삭제
    @Override
    public void deleteTask(String userId, Long taskId) {
        String url = taskApiUrl + "/tasks/" + taskId;
        HttpHeaders headers = createAuthHeaders(userId);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
    }

    // ------------------------------------------------------------------------------------

    // (로그인한 사용자) 댓글을 생성
    @Override
    public void createComment(String userId, Long taskId, CommentCreateRequest request) {
        String url = taskApiUrl + "/tasks/" + taskId + "/comments";
        log.info("[TaskApiClient] createComment: {} (User: {})", url, userId);

        HttpHeaders headers = createAuthHeaders(userId);
        HttpEntity<CommentCreateRequest> entity = new HttpEntity<>(request, headers);

        restTemplate.postForObject(url, entity, Void.class);
    }

    // 댓글 수정
    @Override
    public void updateComment(String userId, Long commentId, CommentUpdateRequest request) {
        String url = taskApiUrl + "/comments/" + commentId;
        HttpHeaders headers = createAuthHeaders(userId);
        HttpEntity<CommentUpdateRequest> entity = new HttpEntity<>(request, headers);
        restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);
    }

    // 댓글 삭제
    @Override
    public void deleteComment(String userId, Long commentId) {
        String url = taskApiUrl + "/comments/" + commentId;
        HttpHeaders headers = createAuthHeaders(userId);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
    }

    // ------------------------------------------------------------------------------------

    // 프로젝트의 태그 목록 조회
    @Override
    public List<TagResponse> getTags(String userId, Long projectId) {
        String url = taskApiUrl + "/projects/" + projectId + "/tags";
        log.info("[TaskApiClient] getTags: {} (User: {})", url, userId);

        HttpHeaders headers = createAuthHeaders(userId);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ParameterizedTypeReference<List<TagResponse>> responseType = new ParameterizedTypeReference<>() {};
        ResponseEntity<List<TagResponse>> response =
                restTemplate.exchange(url, HttpMethod.GET, entity, responseType);

        return response.getBody();
    }

    // 태그 생성
    @Override
    public void createTag(String userId, Long projectId, TagCreateRequest request) {
        String url = taskApiUrl + "/projects/" + projectId + "/tags";
        log.info("[TaskApiClient] createTag: {} (User: {})", url, userId);

        HttpHeaders headers = createAuthHeaders(userId);
        HttpEntity<TagCreateRequest> entity = new HttpEntity<>(request, headers);

        restTemplate.postForObject(url, entity, Void.class);
    }

    // 태그 삭제
    @Override
    public void deleteTag(String userId, Long projectId, Long tagId) {
        String url = taskApiUrl + "/projects/" + projectId + "/tags/" + tagId;
        log.info("[TaskApiClient] deleteTag: {} (User: {})", url, userId);

        HttpHeaders headers = createAuthHeaders(userId);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
    }

    // 태그 수정
    @Override
    public void updateTag(String userId, Long projectId, Long tagId, TagUpdateRequest request) {
        String url = taskApiUrl + "/projects/" + projectId + "/tags/" + tagId;
        HttpHeaders headers = createAuthHeaders(userId);
        HttpEntity<TagUpdateRequest> entity = new HttpEntity<>(request, headers);
        restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);
    }

    // ------------------------------------------------------------------------------------

    // 프로젝트의 마일스톤 목록 조회
    @Override
    public List<MilestoneResponse> getMilestones(String userId, Long projectId) {
        String url = taskApiUrl + "/projects/" + projectId + "/milestones";
        log.info("[TaskApiClient] getMilestones: {} (User: {})", url, userId);

        HttpHeaders headers = createAuthHeaders(userId);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ParameterizedTypeReference<List<MilestoneResponse>> responseType = new ParameterizedTypeReference<>() {};
        ResponseEntity<List<MilestoneResponse>> response =
                restTemplate.exchange(url, HttpMethod.GET, entity, responseType);

        return response.getBody();
    }

    // 마일스톤 생성
    @Override
    public void createMilestone(String userId, Long projectId, MilestoneCreateRequest request) {
        String url = taskApiUrl + "/projects/" + projectId + "/milestones";
        log.info("[TaskApiClient] createMilestone: {} (User: {})", url, userId);

        HttpHeaders headers = createAuthHeaders(userId);
        HttpEntity<MilestoneCreateRequest> entity = new HttpEntity<>(request, headers);

        restTemplate.postForObject(url, entity, Void.class);
    }

    // 마일스톤 수정
    @Override
    public void updateMilestone(String userId, Long projectId, Long milestoneId, MilestoneUpdateRequest request) {
        String url = taskApiUrl + "/projects/" + projectId + "/milestones/" + milestoneId;
        HttpHeaders headers = createAuthHeaders(userId);
        HttpEntity<MilestoneUpdateRequest> entity = new HttpEntity<>(request, headers);
    }

    // 마일스톤 삭제
    @Override
    public void deleteMilestone(String userId, Long projectId, Long milestoneId) {
        String url = taskApiUrl + "/projects/" + projectId + "/milestones/" + milestoneId;
        log.info("[TaskApiClient] deleteMilestone: {} (User: {})", url, userId);

        HttpHeaders headers = createAuthHeaders(userId);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
    }
}