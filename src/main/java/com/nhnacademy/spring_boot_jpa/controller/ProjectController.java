package com.nhnacademy.spring_boot_jpa.controller;

import com.nhnacademy.spring_boot_jpa.dto.project.ProjectCreateRequest;
import com.nhnacademy.spring_boot_jpa.dto.project.ProjectDetailsResponse;
import com.nhnacademy.spring_boot_jpa.dto.project.ProjectResponse;
import com.nhnacademy.spring_boot_jpa.dto.account.UserPrincipal;
import com.nhnacademy.spring_boot_jpa.dto.project.ProjectUpdateRequest;
import com.nhnacademy.spring_boot_jpa.service.taskapi.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {

//    private final TaskApiClient taskApiClient;
    private final ProjectService projectService;

    // 루트(/) 요청 시 프로젝트 목록으로 리다이렉트
    @GetMapping("/")
    public String redirectToProjectList() {
        return "redirect:/projects";
    }

    // URL : GET /projects
    @GetMapping
    public String showProjectList(@AuthenticationPrincipal UserPrincipal user,
                                  Model model) {

        long memberId = user.getId();
        log.info("member id : {}", memberId);

        try {
            List<ProjectResponse> projects = projectService.getMyProjects(memberId);
            model.addAttribute("projects", projects);

            return "projectList";

        } catch (Exception e) {
            log.error("Could not load projects from API", e);
            model.addAttribute("error", "프로젝트 목록을 불러오는 데 실패했습니다.");
            return "projectList";
        }
    }

    // URL : GET /projects/new
    @GetMapping("/new")
    public String showCreateProjectForm(Model model) {
        model.addAttribute("projectCreateRequest", new ProjectCreateRequest());
        return "projectForm";
    }

    // URL : POST /projects
    @PostMapping
    public String createProject(@ModelAttribute ProjectCreateRequest request,
                                @AuthenticationPrincipal UserPrincipal user) {
        try {
            projectService.createProject(user.getId(), request);
            return "redirect:/projects";
        } catch (Exception e) {
            log.error("Failed to create project", e);
            return "redirect:/projects/new?error=true";
        }
    }

    // URL : GET /projects/{projectId}/edit
    @GetMapping("/{projectId}/edit")
    public String showUpdateProjectForm(@PathVariable long projectId,
                                        @AuthenticationPrincipal UserPrincipal user,
                                        Model model) {
        ProjectDetailsResponse project = projectService.getProjectDetails(user.getId(), projectId);

        // ProjectDetailsResponse -> ProjectUpdateRequest 변환
        ProjectUpdateRequest request = new ProjectUpdateRequest();
        request.setProjectName(project.getProjectName());
        request.setProjectStatus(project.getProjectStatus());

        model.addAttribute("projectUpdateRequest", request);
        model.addAttribute("projectId", projectId);
        model.addAttribute("isUpdate", true);

        return "projectForm";
    }

    // URL : POST /projects/{projectId}/update
    @PostMapping("/{projectId}/update")
    public String updateProject(@PathVariable long projectId,
                                @ModelAttribute ProjectUpdateRequest request,
                                @AuthenticationPrincipal UserPrincipal user) {
        try {
            projectService.updateProject(user.getId(), projectId, request);
            return "redirect:/projects/" + projectId;
        } catch (Exception e) {
            log.error("Failed to update project", e);
            return "redirect:/projects/" + projectId + "/edit?error=project_update_failed";
        }
    }

    // URL : GET /projects/{projectId}
    @GetMapping("/{projectId}")
    public String showProjectDetails(@PathVariable long projectId,
                                     @AuthenticationPrincipal UserPrincipal user,
                                     Model model) {
        long memberId = user.getId();
        log.info("request /projects/{} by members: {}", projectId, memberId);

        try {
            ProjectDetailsResponse projectDetails = projectService.getProjectDetails(memberId, projectId);
            model.addAttribute("project", projectDetails);

            return "projectDetails";
        } catch (Exception e) {
            log.error("Could not load project details for member: {}", memberId, e);
            return "redirect:/projects?error=project_details_failed";
        }
    }
}