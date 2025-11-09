package com.nhnacademy.spring_boot_jpa.controller;

import com.nhnacademy.spring_boot_jpa.dto.account.UserPrincipal;
import com.nhnacademy.spring_boot_jpa.dto.comment.CommentCreateRequest;
import com.nhnacademy.spring_boot_jpa.service.TaskApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/projects/{projectId}/tasks/{taskId}/comments")
public class CommentController {

    private final TaskApiClient taskApiClient;

    // 댓글 생성
    // URL: POST /projects/{projectId}/tasks/{taskId}/comments
    @PostMapping
    public String createComment(@PathVariable Long projectId,
                                @PathVariable Long taskId,
                                @ModelAttribute CommentCreateRequest request,
                                @AuthenticationPrincipal UserPrincipal user) {
        Long memberId = user.getId();

        try {
            taskApiClient.createComment(memberId, taskId, request);
            return String.format("redirect:/projects/%s/tasks/%s", projectId, taskId);
        } catch (Exception e) {
            log.error("Failed to create comment", e);
            return "redirect:/tasks/" + taskId + "?error=comment_create_failed";
        }
    }

    // 댓글 삭제
    // URL: POST /projects/{projectId}/tasks/{taskId}/comments/{commentId}/delete
    @PostMapping("/{commentId}/delete")
    public String deleteComment(@PathVariable Long projectId,
                                @PathVariable Long taskId,
                                @ModelAttribute CommentCreateRequest request,
                                @AuthenticationPrincipal UserPrincipal user) {
        Long memberId = user.getId();

        try {
//            taskApiClient.deleteComment(memberId, taskId, request);
            return String.format("redirect:/projects/%s/tasks/%s", projectId, taskId);
        } catch (Exception e) {
            log.error("Failed to create comment", e);
            return "redirect:/tasks/" + taskId + "?error=comment_delete_failed";
        }
    }
}
