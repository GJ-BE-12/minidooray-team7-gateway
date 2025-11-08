package com.nhnacademy.spring_boot_jpa.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map; // 또는 ProjectDto

@Slf4j
@Controller
@RequiredArgsConstructor
public class ProjectController {

    private final RestTemplate restTemplate;

    // application.yaml 또는 properties에 Task-Api 주소 설정 필요
    @Value("${api.task.url}")
    private String taskApiUrl;

    /**
     * 4단계: 프로젝트 목록 화면
     */
    @GetMapping("/projectList")
    public String showProjectList(Model model) {

        // Task-Api의 프로젝트 목록 엔드포인트 (예: http://localhost:8082/projects)
        String url = taskApiUrl + "/projects";
        log.info("Fetching project list from: {}", url);

        try {
            // 4단계: RestTemplate으로 Task-Api 호출
            // Task-Api가 List<ProjectDto> 형태를 반환한다고 가정
            // 여기서는 List<Map<String, Object>>로 받습니다. (ProjectDto로 대체 권장)
            ParameterizedTypeReference<List<Map<String, Object>>> responseType =
                    new ParameterizedTypeReference<>() {};

            ResponseEntity<List<Map<String, Object>>> response =
                    restTemplate.exchange(url, HttpMethod.GET, null, responseType);

            model.addAttribute("projects", response.getBody());

            return "projectList"; // templates/projectList.html (Thymeleaf 뷰)

        } catch (Exception e) {
            log.error("Could not load projects from API", e);
            model.addAttribute("error", "프로젝트 목록을 불러오는 데 실패했습니다.");
            return "projectList"; // 에러가 있어도 페이지는 보여줌
        }
    }

    // 루트(/) 요청 시 프로젝트 목록으로 리다이렉트
    @GetMapping("/")
    public String redirectToProjectList() {
        return "redirect:/projectList";
    }
}