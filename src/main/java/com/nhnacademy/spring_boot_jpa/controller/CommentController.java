package com.nhnacademy.spring_boot_jpa.controller;

import com.nhnacademy.spring_boot_jpa.dto.account.UserPrincipal;
import com.nhnacademy.spring_boot_jpa.dto.comment.CommentCreateRequest;
import com.nhnacademy.spring_boot_jpa.dto.comment.CommentUpdateRequest;
import com.nhnacademy.spring_boot_jpa.service.taskapi.CommentService;
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

//    private final TaskApiClient taskApiClient;
    private final CommentService commentService;

    // URL: POST /projects/{projectId}/tasks/{taskId}/comments
    @PostMapping
    public String createComment(@PathVariable Long projectId,
                                @PathVariable Long taskId,
                                @ModelAttribute CommentCreateRequest request,
                                @AuthenticationPrincipal UserPrincipal user) {
        Long memberId = user.getId();

        try {
            commentService.createComment(memberId, taskId, request);
            return String.format("redirect:/projects/%s/tasks/%s", projectId, taskId);
        } catch (Exception e) {
            log.error("Failed to create comment", e);
            return String.format("redirect:/projects/%s/tasks/%s?error=comment_create_failed", projectId, taskId);
        }
    }

    // URL: POST /projects/{projectId}/tasks/{taskId}/comments/{commentId}/update
    @PostMapping("/{commentId}/update")
    public String updateComment(@PathVariable Long projectId,
                                @PathVariable Long taskId,
                                @PathVariable Long commentId,
                                @ModelAttribute CommentUpdateRequest request, // content만 있는 DTO
                                @AuthenticationPrincipal UserPrincipal user) {
        Long memberId = user.getId();

        try {
            // TaskApi는 commentId 작성자와 memberId가 일치하는지 확인해야 함
            commentService.updateComment(memberId, commentId, request);
            return String.format("redirect:/projects/%s/tasks/%s", projectId, taskId);
        } catch (Exception e) {
            log.error("Failed to update comment", e);
            return String.format("redirect:/projects/%s/tasks/%s?error=comment_update_failed", projectId, taskId);
        }
    }

     // URL: POST /projects/{projectId}/tasks/{taskId}/comments/{commentId}/delete
    @PostMapping("/{commentId}/delete")
    public String deleteComment(@PathVariable Long projectId,
                                @PathVariable Long taskId,
                                @PathVariable Long commentId,
                                @AuthenticationPrincipal UserPrincipal user) {
        Long memberId = user.getId();

        try {
            commentService.deleteComment(memberId, commentId);
            return String.format("redirect:/projects/%s/tasks/%s", projectId, taskId);
        } catch (Exception e) {
            log.error("Failed to delete comment", e);
            return String.format("redirect:/projects/%s/tasks/%s?error=comment_delete_failed", projectId, taskId);
        }
    }
}
