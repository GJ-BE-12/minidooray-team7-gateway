package com.nhnacademy.spring_boot_jpa.controller;

import com.nhnacademy.spring_boot_jpa.dto.account.UserPrincipal;
import com.nhnacademy.spring_boot_jpa.dto.milestone.MilestoneCreateRequest;
import com.nhnacademy.spring_boot_jpa.dto.milestone.MilestoneResponse;
import com.nhnacademy.spring_boot_jpa.dto.milestone.MilestoneUpdateRequest;
import com.nhnacademy.spring_boot_jpa.service.taskapi.MilestoneService;
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

//    private final TaskApiClient taskApiClient;
    private final MilestoneService milestoneService;

    // URL: GET /projects/{projectId}/milestones
    @GetMapping
    public String showMilestoneList(@PathVariable Long projectId,
                                    @AuthenticationPrincipal UserPrincipal user,
                                    Model model) {
        Long memberId = user.getId();
        try {
            List<MilestoneResponse> milestones = milestoneService.getMilestones(memberId, projectId);
            model.addAttribute("milestones", milestones);
            model.addAttribute("projectId", projectId);
            model.addAttribute("milestoneCreateRequest", new MilestoneCreateRequest());
            return "milestoneList";
        } catch (Exception e) {
            log.error("Failed to load milestones", e);
            return "redirect:/projects/" + projectId + "?error=milestones_show_failed";
        }
    }

    // URL: POST /projects/{projectId}/milestones
    @PostMapping
    public String createMilestone(@PathVariable Long projectId,
                                  @ModelAttribute MilestoneCreateRequest request,
                                  @AuthenticationPrincipal UserPrincipal user) {
        Long memberId = user.getId();
        try {
            milestoneService.createMilestone(memberId, projectId, request);
            return "redirect:/projects/" + projectId + "/milestones";
        } catch (Exception e) {
            log.error("Failed to create milestone", e);
            return "redirect:/projects/" + projectId + "/milestones?error=milestone_create_failed";
        }
    }

    // URL: GET /projects/{projectId}/milestones/{milestoneId}/edit
    @GetMapping("/{milestoneId}/edit")
    public String showUpdateMilestoneForm(@PathVariable Long projectId,
                                          @PathVariable Long milestoneId,
                                          Model model) {
        model.addAttribute("milestoneUpdateRequest", new MilestoneUpdateRequest());
        model.addAttribute("projectId", projectId);
        model.addAttribute("milestoneId", milestoneId);
        return "milestoneUpdateForm";
    }

    // URL: POST /projects/{projectId}/milestones/{milestoneId}/update
    @PostMapping("/{milestoneId}/update")
    public String updateMilestone(@PathVariable Long projectId,
                                  @PathVariable Long milestoneId,
                                  @ModelAttribute MilestoneUpdateRequest request,
                                  @AuthenticationPrincipal UserPrincipal user) {
        try {
            milestoneService.updateMilestone(user.getId(), projectId, milestoneId, request);
            return "redirect:/projects/" + projectId + "/milestones";
        } catch (Exception e) {
            log.error("Failed to update milestone", e);
            return "redirect:/projects/" + projectId + "/milestones/" + milestoneId + "/edit?error=true";
        }
    }

    // URL: POST /projects/{projectId}/milestones/{milestoneId}/delete
    @PostMapping("/{milestoneId}/delete")
    public String deleteMilestone(@PathVariable Long projectId,
                                  @PathVariable Long milestoneId,
                                  @AuthenticationPrincipal UserPrincipal user) {
        Long memberId = user.getId();
        try {
            milestoneService.deleteMilestone(memberId, projectId, milestoneId);
            return "redirect:/projects/" + projectId + "/milestones";
        } catch (Exception e) {
            log.error("Failed to delete milestone", e);
            return "redirect:/projects/" + projectId + "/milestones?error=milestone_delete_failed";
        }
    }
}