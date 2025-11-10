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
        String userId = user.getUserId();
        try {
            List<TagResponse> tags = tagService.getTags(userId, projectId);
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
        String userId = user.getUserId();
        try {
            tagService.createTag(userId, projectId, request);
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
        model.addAttribute("tagUpdateRequest", new TagUpdateRequest());
        model.addAttribute("projectId", projectId);
        model.addAttribute("tagId", tagId);
        return "tagUpdateForm";
    }

    // URL: PUT /projects/{projectId}/tags/{tagId}
    @PutMapping("/{tagId}")
    public String updateTag(@PathVariable Long projectId,
                            @PathVariable Long tagId,
                            @ModelAttribute TagUpdateRequest request,
                            @AuthenticationPrincipal UserPrincipal user) {
        try {
            tagService.updateTag(user.getUserId(), projectId, tagId, request);
            return "redirect:/projects/" + projectId + "/tags";
        } catch (Exception e) {
            log.error("Failed to update tag", e);
            return "redirect:/projects/" + projectId + "/tags/" + tagId + "/edit?error=true";
        }
    }

    // URL: DELETE /projects/{projectId}/tags/{tagId}
    @DeleteMapping("/{tagId}")
    public String deleteTag(@PathVariable Long projectId,
                            @PathVariable Long tagId,
                            @AuthenticationPrincipal UserPrincipal user) {
        String userId = user.getUserId();
        try {
            tagService.deleteTag(userId, projectId, tagId);
            return "redirect:/projects/" + projectId + "/tags";
        } catch (Exception e) {
            log.error("Failed to delete tag", e);
            return "redirect:/projects/" + projectId + "/tags?error=tag_delete_failed";
        }
    }
}
