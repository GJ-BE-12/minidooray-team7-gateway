package com.nhnacademy.spring_boot_jpa.controller;

import com.nhnacademy.spring_boot_jpa.dto.ProjectDetailsResponse;
import com.nhnacademy.spring_boot_jpa.dto.ProjectResponse;
import com.nhnacademy.spring_boot_jpa.dto.TaskCreateRequest;
import com.nhnacademy.spring_boot_jpa.dto.account.UserPrincipal;
import com.nhnacademy.spring_boot_jpa.service.TaskApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ProjectController {

    //    private final RestTemplate restTemplate;
    private final TaskApiClient taskApiClient;

    // 4단계: 프로젝트 목록 화면 (인증 적용)
    @GetMapping("/projectList")
    public String showProjectList(@AuthenticationPrincipal UserPrincipal user,
                                  Model model) {

        long memberId = user.getId();
        log.info("request /projectList by member: {}", memberId);

        try {
            List<ProjectResponse> projects = taskApiClient.getMyProjects(memberId);
            model.addAttribute("projects", projects);

            return "projectList";

        } catch (Exception e) {
            log.error("Could not load projects from API", e);
            model.addAttribute("error", "프로젝트 목록을 불러오는 데 실패했습니다.");
            return "projectList"; // 에러가 있어도 페이지는 보여줌
        }
    }


    // 프로젝트 상세 페이지
    @GetMapping("/projects/{projectId}")
    public String showProjectDetails(@PathVariable long projectId,
                                     @AuthenticationPrincipal UserPrincipal user,
                                     Model model) {
        long memberId = user.getId();
        log.info("request /projects/{} by members: {}", projectId, memberId);

        try {
            ProjectDetailsResponse projectDetails = taskApiClient.getProjectDetails(memberId, projectId);
            model.addAttribute("project", projectDetails);

            return "projectDetails";
        } catch (Exception e) {
            log.error("Could not load project details for member: {}", memberId, e);
            return "redirect:/projectList?error=details_failed";
        }
    }


    // 태스크 생성 폼 페이지
    @GetMapping("/projects/{projectId}/tasks/new")
    public String showCreateTaskForm(@PathVariable long projectId,
                                     Model model) {
        model.addAttribute("taskCreateRequest", new TaskCreateRequest());
        model.addAttribute("projectId", projectId);

        return "taskForm";
    }

    // 루트(/) 요청 시 프로젝트 목록으로 리다이렉트
    @GetMapping("/")
    public String redirectToProjectList() {
        return "redirect:/projectList";
    }
}