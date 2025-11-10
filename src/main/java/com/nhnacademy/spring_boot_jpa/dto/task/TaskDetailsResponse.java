package com.nhnacademy.spring_boot_jpa.dto.task;

import com.nhnacademy.spring_boot_jpa.dto.comment.CommentResponse;
import com.nhnacademy.spring_boot_jpa.dto.milestone.MilestoneResponse;
import com.nhnacademy.spring_boot_jpa.dto.tag.TagResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

// Task-Api로부터 태스크 상세 정보를 받기 위한 DTO
@Getter
@Setter
@NoArgsConstructor
public class TaskDetailsResponse {
    private Long taskId;
    private Long projectId;
    private String title;
    private String body;
    private String authorName;
    private List<CommentResponse> comments;

    // 마일스톤 및 태그 정보 추가
    private MilestoneResponse milestone;
    private List<TagResponse> tags;
}