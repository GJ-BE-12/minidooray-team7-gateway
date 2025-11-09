package com.nhnacademy.spring_boot_jpa.controller;

import com.nhnacademy.spring_boot_jpa.dto.account.UserPrincipal;
import com.nhnacademy.spring_boot_jpa.dto.milestone.MilestoneCreateRequest;
import com.nhnacademy.spring_boot_jpa.dto.milestone.MilestoneResponse;
import com.nhnacademy.spring_boot_jpa.service.TaskApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/projects/{projectId}/milestones")
public class MilestoneController {

    private final TaskApiClient taskApiClient;

    // 마일스톤 관리 페이지 (목록 조회 및 생성 폼)
    // URL: GET /projects/{projectId}/milestones
    @GetMapping
    public String showMilestoneList(@PathVariable Long projectId,
                                    @AuthenticationPrincipal UserPrincipal user,
                                    Model model) {
        Long memberId = user.getId();
        try {
            List<MilestoneResponse> milestones = taskApiClient.getMilestones(memberId, projectId);
            model.addAttribute("milestones", milestones);
            model.addAttribute("projectId", projectId);
            model.addAttribute("milestoneCreateRequest", new MilestoneCreateRequest());
            return "milestoneList"; // (신규 템플릿) // todo : 마일스톤 어떻게 보여줘야되지..
        } catch (Exception e) {
            log.error("Failed to load milestones", e);
            return "redirect:/projects/" + projectId + "?error=milestones_failed";
        }
    }

    // 마일스톤 생성 처리
    // URL: POST /projects/{projectId}/milestones
    @PostMapping
    public String createMilestone(@PathVariable Long projectId,
                                  @ModelAttribute MilestoneCreateRequest request,
                                  @AuthenticationPrincipal UserPrincipal user) {
        Long memberId = user.getId();
        try {
            taskApiClient.createMilestone(memberId, projectId, request);
            return "redirect:/projects/" + projectId + "/milestones";
        } catch (Exception e) {
            log.error("Failed to create milestone", e);
            return "redirect:/projects/" + projectId + "/milestones?error=milestone_create_failed";
        }
    }

    // 마일스톤 삭제 처리
    // URL: DELETE /projects/{projectId}/milestones/{milestoneId}/delete
    @DeleteMapping("/{milestoneId}/delete")
    public String deleteMilestone(@PathVariable Long projectId,
                                  @PathVariable Long milestoneId,
                                  @AuthenticationPrincipal UserPrincipal user) {
        Long memberId = user.getId();
        try {
            taskApiClient.deleteMilestone(memberId, projectId, milestoneId);
            return "redirect:/projects/" + projectId + "/milestones";
        } catch (Exception e) {
            log.error("Failed to delete milestone", e);
            return "redirect:/projects/" + projectId + "/milestones?error=milestone_delete_failed";
        }
    }
}