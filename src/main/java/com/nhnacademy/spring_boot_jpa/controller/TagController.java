package com.nhnacademy.spring_boot_jpa.controller;

import com.nhnacademy.spring_boot_jpa.dto.account.UserPrincipal;
import com.nhnacademy.spring_boot_jpa.dto.tag.TagCreateRequest;
import com.nhnacademy.spring_boot_jpa.dto.tag.TagResponse;
import com.nhnacademy.spring_boot_jpa.dto.tag.TagUpdateRequest;
import com.nhnacademy.spring_boot_jpa.service.taskapi.TagService;
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

//    private final TaskApiClient taskApiClient;
    private final TagService tagService;

    // URL: GET /projects/{projectId}/tags
    @GetMapping
    public String showTagList(@PathVariable Long projectId,
                              @AuthenticationPrincipal UserPrincipal user,
                              Model model) {
        Long memberId = user.getId();
        try {
            List<TagResponse> tags = tagService.getTags(memberId, projectId);
            model.addAttribute("tags", tags);
            model.addAttribute("projectId", projectId);
            model.addAttribute("tagCreateRequest", new TagCreateRequest());
            return "tagList";
        } catch (Exception e) {
            log.error("Failed to load tags", e);
            return "redirect:/projects/" + projectId + "?error=tags_show_failed";
        }
    }

    // URL: POST /projects/{projectId}/tags
    @PostMapping
    public String createTag(@PathVariable Long projectId,
                            @ModelAttribute TagCreateRequest request,
                            @AuthenticationPrincipal UserPrincipal user) {
        Long memberId = user.getId();
        try {
            tagService.createTag(memberId, projectId, request);
            return "redirect:/projects/" + projectId + "/tags";
        } catch (Exception e) {
            log.error("Failed to create tag", e);
            return "redirect:/projects/" + projectId + "/tags?error=tag_create_failed";
        }
    }

    // URL: POST /projects/{projectId}/tags/{tagsId}/edit
    @GetMapping("/{tagId}/edit")
    public String showUpdateTagForm(@PathVariable Long projectId,
                                    @PathVariable Long tagId,
                                    Model model) {
        // (단순화) 기존 이름을 알 수 없으므로 빈 폼 제공
        // (개선) getTagDetails API가 있다면 기존 정보 조회
        model.addAttribute("tagUpdateRequest", new TagUpdateRequest());
        model.addAttribute("projectId", projectId);
        model.addAttribute("tagId", tagId);
        return "tagUpdateForm"; // 템플릿 (4번 항목 참고)
    }

    // URL: POST /projects/{projectId}/tags/{tagsId}/update
    @PostMapping("/{tagId}/update")
    public String updateTag(@PathVariable Long projectId,
                            @PathVariable Long tagId,
                            @ModelAttribute TagUpdateRequest request,
                            @AuthenticationPrincipal UserPrincipal user) {
        try {
            tagService.updateTag(user.getId(), projectId, tagId, request);
            return "redirect:/projects/" + projectId + "/tags";
        } catch (Exception e) {
            log.error("Failed to update tag", e);
            return "redirect:/projects/" + projectId + "/tags/" + tagId + "/edit?error=true";
        }
    }

    // URL: GET /projects/{projectId}/tags/{tags}/delete
    @PostMapping("/{tagId}/delete")
    public String deleteTag(@PathVariable Long projectId,
                            @PathVariable Long tagId,
                            @AuthenticationPrincipal UserPrincipal user) {
        Long memberId = user.getId();
        try {
            tagService.deleteTag(memberId, projectId, tagId);
            return "redirect:/projects/" + projectId + "/tags";
        } catch (Exception e) {
            log.error("Failed to delete tag", e);
            return "redirect:/projects/" + projectId + "/tags?error=tag_delete_failed";
        }
    }
}
