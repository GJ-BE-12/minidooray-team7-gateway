package com.nhnacademy.spring_boot_jpa.controller;

import com.nhnacademy.spring_boot_jpa.dto.comment.CommentCreateRequest;
import com.nhnacademy.spring_boot_jpa.dto.task.TaskCreateRequest;
import com.nhnacademy.spring_boot_jpa.dto.task.TaskDetailsResponse;
import com.nhnacademy.spring_boot_jpa.dto.account.UserPrincipal;
import com.nhnacademy.spring_boot_jpa.dto.task.TaskUpdateRequest;
import com.nhnacademy.spring_boot_jpa.service.taskapi.MilestoneService;
import com.nhnacademy.spring_boot_jpa.service.taskapi.TagService;
import com.nhnacademy.spring_boot_jpa.service.taskapi.TaskService;
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

//    private final TaskApiClient taskApiClient;
    private final TaskService taskService;
    private final TagService tagService;
    private final MilestoneService milestoneService;

    // URL : GET /projects/{projectId}/tasks/new
    @GetMapping("/new")
    public String showCreateTaskForm(@PathVariable long projectId,
                                     Model model,
                                     @AuthenticationPrincipal UserPrincipal user) {
        model.addAttribute("taskCreateRequest", new TaskCreateRequest());
        model.addAttribute("projectId", projectId);
        model.addAttribute("tags", tagService.getTags(user.getUserId(), projectId));
        model.addAttribute("milestones", milestoneService.getMilestones(user.getUserId(), projectId));
        return "taskForm";
    }

    // URL : POST /projects/{projectId}/tasks
    @PostMapping
    public String createTask(@PathVariable Long projectId,
                             @ModelAttribute TaskCreateRequest request,
                             @AuthenticationPrincipal UserPrincipal user) {
        String userId = user.getUserId();

        try {
            taskService.createTask(userId, projectId, request);
            return "redirect:/projects/" + projectId;
        } catch (Exception e) {
            log.error("Failed to create task", e);
            return "redirect:/projects/" + projectId + "/tasks/new?error=task_create_failed";
        }
    }

    // URL: GET /projects/{projectId}/tasks/{taskId}
    @GetMapping("/{taskId}")
    public String showTaskDetails(@PathVariable Long projectId,
                                  @PathVariable Long taskId,
                                  @AuthenticationPrincipal UserPrincipal user,
                                  Model model) {
        String userId = user.getUserId();

        try {
            TaskDetailsResponse taskDetails = taskService.getTaskDetails(userId, taskId);
            model.addAttribute("task", taskDetails);
            model.addAttribute("commentCreateRequest", new CommentCreateRequest());
            model.addAttribute("projectId", projectId);

            return "taskDetails";
        } catch (Exception e) {
            log.error("Could not load task details for member: {}", userId, e);
            return "redirect:/projects/" + projectId + "?error=task_details_failed";
        }
    }

    // URL: GET /projects/{projectId}/tasks/{taskId}/edit
    @GetMapping("/{taskId}/edit")
    public String showTaskUpdateForm(@PathVariable Long projectId,
                                     @PathVariable Long taskId,
                                     @AuthenticationPrincipal UserPrincipal user,
                                     Model model) {

        TaskDetailsResponse task = taskService.getTaskDetails(user.getUserId(), taskId);

        // TaskDetailsResponse -> TaskUpdateRequest 변환
        TaskUpdateRequest request = new TaskUpdateRequest();
        request.setTitle(task.getTitle());
        request.setContent(task.getContent());
        if (task.getMilestone() != null) {
            request.setMilestoneId(task.getMilestone().getMilestoneId());
        }
        // ... 태그 ID 목록도 변환 필요 ...

        model.addAttribute("taskUpdateRequest", request);
        model.addAttribute("projectId", projectId);
        model.addAttribute("taskId", taskId);

        // (보완) 태그, 마일스톤 목록을 불러와 모델에 추가해야 함
        model.addAttribute("tags", tagService.getTags(user.getUserId(), projectId));
        model.addAttribute("milestones", milestoneService.getMilestones(user.getUserId(), projectId));

        return "taskUpdateForm"; // 템플릿 (4번 항목 참고)
    }

    // URL: GET /projects/{projectId}/tasks/{taskId}/update
    @PostMapping("/{taskId}/update")
    public String updateTask(@PathVariable Long projectId,
                             @PathVariable Long taskId,
                             @ModelAttribute TaskUpdateRequest request,
                             @AuthenticationPrincipal UserPrincipal user) {
        try {
            taskService.updateTask(user.getUserId(), taskId, request);
            return String.format("redirect:/projects/%d/tasks/%d", projectId, taskId);
        } catch (Exception e) {
            log.error("Failed to update task", e);
            return String.format("redirect:/projects/%d/tasks/%d/edit?error=true", projectId, taskId);
        }
    }

    // URL: GET /projects/{projectId}/tasks/{taskId}/delete
    @PostMapping("/{taskId}/delete")
    public String deleteTask(@PathVariable Long projectId,
                             @PathVariable Long taskId,
                             @AuthenticationPrincipal UserPrincipal user) {
        try {
            taskService.deleteTask(user.getUserId(), taskId);
            return "redirect:/projects/" + projectId;
        } catch (Exception e) {
            log.error("Failed to delete task", e);
            return String.format("redirect:/projects/%d/tasks/%d?error=task_delete_failed", projectId, taskId);
        }
    }
}
