package com.nhnacademy.spring_boot_jpa.dto.task;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// task-api로 태스크 생성 요청을 위한 dto
@Data
@NoArgsConstructor
public class TaskCreateRequest {
    private String title;
    private String content;

    private Long milestoneId;
    private List<Long> tagIds;
}