package com.nhnacademy.spring_boot_jpa.controller;

import com.nhnacademy.spring_boot_jpa.dto.account.UserPrincipal;
import com.nhnacademy.spring_boot_jpa.dto.tag.TagCreateRequest;
import com.nhnacademy.spring_boot_jpa.dto.tag.TagResponse;
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
@RequestMapping("/projects/{projectId}/tags")
public class TagController {

    private final TaskApiClient taskApiClient;

    // 태그 관리 페이지 (목록 조회 및 생성 폼)
    // URL: GET /projects/{projectId}/tags
    @GetMapping
    public String showTagList(@PathVariable Long projectId,
                              @AuthenticationPrincipal UserPrincipal user,
                              Model model) {
        Long memberId = user.getId();
        try {
            List<TagResponse> tags = taskApiClient.getTags(memberId, projectId);
            model.addAttribute("tags", tags);
            model.addAttribute("projectId", projectId);
            model.addAttribute("tagCreateRequest", new TagCreateRequest());
            return "tagList"; // (신규 템플릿) // todo: task목록과 task상세페이지에 같이 보이도록...
        } catch (Exception e) {
            log.error("Failed to load tags", e);
            return "redirect:/projects/" + projectId + "?error=tags_failed";
        }
    }

    // 태그 생성 처리
    // URL: POST /projects/{projectId}/tags
    @PostMapping
    public String createTag(@PathVariable Long projectId,
                            @ModelAttribute TagCreateRequest request,
                            @AuthenticationPrincipal UserPrincipal user) {
        Long memberId = user.getId();
        try {
            taskApiClient.createTag(memberId, projectId, request);
            return "redirect:/projects/" + projectId + "/tags";
        } catch (Exception e) {
            log.error("Failed to create tag", e);
            return "redirect:/projects/" + projectId + "/tags?error=tag_create_failed";
        }
    }

    // 태그 삭제 처리
    // URL: GET /projects/{projectId}/tags/{tags}
    @DeleteMapping("/{tagId}")
    public String deleteTag(@PathVariable Long projectId,
                            @PathVariable Long tagId,
                            @AuthenticationPrincipal UserPrincipal user) {
        Long memberId = user.getId();
        try {
            taskApiClient.deleteTag(memberId, projectId, tagId);
            return "redirect:/projects/" + projectId + "/tags";
        } catch (Exception e) {
            log.error("Failed to delete tag", e);
            return "redirect:/projects/" + projectId + "/tags?error=tag_delete_failed";
        }
    }
}
