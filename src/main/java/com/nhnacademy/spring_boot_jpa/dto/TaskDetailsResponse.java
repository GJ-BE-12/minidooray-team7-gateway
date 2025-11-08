package com.nhnacademy.spring_boot_jpa.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// Task-Api로부터 태스크 상세 정보를 받기 위한 DTO
@Data
@NoArgsConstructor
public class TaskDetailsResponse {
    private Long taskId;
    private Long projectId;
    private String title;
    private String content;
    private String authorName;
    private List<CommentResponse> comments;

    // 마일스톤, 태그 등..
}