package com.nhnacademy.spring_boot_jpa.controller;

import com.nhnacademy.spring_boot_jpa.dto.comment.CommentCreateRequest;
import com.nhnacademy.spring_boot_jpa.dto.task.TaskCreateRequest;
import com.nhnacademy.spring_boot_jpa.dto.task.TaskDetailsResponse;
import com.nhnacademy.spring_boot_jpa.dto.account.UserPrincipal;
import com.nhnacademy.spring_boot_jpa.service.TaskApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// task 생성 / 수정 / 삭제
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/projects/{projectId}/tasks")
public class TaskController {

    private final TaskApiClient taskApiClient;

    // 태스크 생성 폼 페이지
    // URL : GET /projects/{projectId}/tasks/new
    @GetMapping("/new")
    public String showCreateTaskForm(@PathVariable long projectId,
                                     Model model) {
        model.addAttribute("taskCreateRequest", new TaskCreateRequest());
        model.addAttribute("projectId", projectId);

        return "taskForm";
    }

    // 태스크 생성 처리
    // URL : POST /projects/{projectId}/tasks
    @PostMapping
    public String createTask(@PathVariable Long projectId,
                             @ModelAttribute TaskCreateRequest request,
                             @AuthenticationPrincipal UserPrincipal user) {
        Long memberId = user.getId();

        try {
            taskApiClient.createTask(memberId, projectId, request);
            return "redirect:/projects/" + projectId;
        } catch (Exception e) {
            log.error("Failed to create task", e);
            return "redirect:/projects/" + projectId + "/tasks/new?error=task_create_failed";
        }
    }

    // 태스크 상세 페이지페이지
    // URL: GET /projects/{projectId}/tasks/{taskId}
    @GetMapping("/{taskId}")
    public String showTaskDetails(@PathVariable Long projectId,
                                  @PathVariable Long taskId,
                                  @AuthenticationPrincipal UserPrincipal user,
                                  Model model) {
        Long memberId = user.getId();

        try {
            TaskDetailsResponse taskDetails = taskApiClient.getTaskDetails(memberId, taskId);
            model.addAttribute("task", taskDetails);
            model.addAttribute("commentCreateRequest", new CommentCreateRequest());
            model.addAttribute("projectId", projectId);

            return "taskDetails";
        } catch (Exception e) {
            log.error("Could not load task details for member: {}", memberId, e);
            return "redirect:/projects/" + projectId + "?error=task_details_failed";
        }
    }
}
