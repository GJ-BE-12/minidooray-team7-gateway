package com.nhnacademy.spring_boot_jpa.controller;

import com.nhnacademy.spring_boot_jpa.dto.CommentCreateRequest;
import com.nhnacademy.spring_boot_jpa.dto.TaskCreateRequest;
import com.nhnacademy.spring_boot_jpa.dto.TaskDetailsResponse;
import com.nhnacademy.spring_boot_jpa.dto.account.UserPrincipal;
import com.nhnacademy.spring_boot_jpa.service.TaskApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

// task 생성 / 수정 / 삭제
@Slf4j
@RequiredArgsConstructor
@Controller
public class TaskController {

    private final TaskApiClient taskApiClient;

    // 태스크 생성 처리
    @PostMapping("/projects/{projectId}/tasks")
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
    @GetMapping("/tasks/{taskId}")
    public String showTaskDetails(@PathVariable Long taskId,
                                  @AuthenticationPrincipal UserPrincipal user,
                                  Model model) {
        Long memberId = user.getId();

        try {
            TaskDetailsResponse taskDetails = taskApiClient.getTaskDetails(memberId, taskId);
            model.addAttribute("task", taskDetails);

            model.addAttribute("commentCreateRequest", new CommentCreateRequest());

            return "taskDetails";
        } catch (Exception e) {
            log.error("Could not load task details for member: {}", memberId, e);
            return "redirect:/projectList?error=task_details_failed";
        }
    }


    // 댓글 생성 처리
    @PostMapping("/tasks/{taskId}/comments")
    public String createComment(@PathVariable Long taskId,
                                @ModelAttribute CommentCreateRequest request,
                                @AuthenticationPrincipal UserPrincipal user) {

        Long memberId = user.getId();

        try {
            taskApiClient.createComment(memberId, taskId, request);
            return "redirect:/tasks/" + taskId;
        } catch (Exception e) {
            log.error("Failed to create comment", e);
            return "redirect:/tasks/" + taskId + "?error=comment_failed";
        }
    }
}
