package com.nhnacademy.spring_boot_jpa.controller;

import com.nhnacademy.spring_boot_jpa.dto.account.UserPrincipal;
import com.nhnacademy.spring_boot_jpa.dto.project.*;
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
@RequestMapping("/projects/{projectId}/members")
public class ProjectMemberController {

//    private final TaskApiClient taskApiClient;
    private final ProjectService projectService;

    // URL : GET /projects/{projectId}/members
    @GetMapping
    public String showProjectMembers(@PathVariable long projectId,
                                     @AuthenticationPrincipal UserPrincipal user,
                                     Model model) {
        log.info("project id: {}", projectId);

        try {
            List<ProjectMemberResponse> members = projectService.getProjectMembers(user.getUserId(), projectId);
            model.addAttribute("members", members);
            model.addAttribute("projectId", projectId);
            return "projectMembers";
        } catch (Exception e) {
            log.error("Failed to load project members", e);
            return "redirect:/projects/" + projectId + "?error=members_show_failed";
        }
    }

    // URL : GET /projects/{projectId}/members
    @PostMapping
    public String addProjectMember(@PathVariable long projectId,
                                   @RequestParam("username") String username,
                                   @AuthenticationPrincipal UserPrincipal user) {
        try {
            // TaskApi는 username을 받아 AccountApi에 유저 존재 여부를 확인 후 추가해야 함
            projectService.addProjectMember(user.getUserId(), projectId, username);
        } catch (Exception e) {
            log.error("Failed to add project member", e);
            return "redirect:/projects/" + projectId + "/members?error=member_add_failed";
        }
        return "redirect:/projects/" + projectId + "/members";
    }

    // URL : DELETE /projects/{projectId}/members/{memberId}
    @DeleteMapping("/{userId}")
    public String deleteProjectMember(@PathVariable long projectId,
                                      @PathVariable String userId,
                                      @AuthenticationPrincipal UserPrincipal user) {
        try {
            projectService.deleteProjectMember(user.getUserId(), projectId, userId);
            log.info("{} 삭제", userId);
        } catch (Exception e) {
            log.error("Failed to delete project member", e);
            return "redirect:/projects/" + projectId + "/members?error=member_delete_failed";
        }
        return "redirect:/projects/" + projectId + "/members";
    }
}