package com.nhnacademy.spring_boot_jpa.dto.task;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

// 태스크 수정을 위한 DTO
@Data
@NoArgsConstructor
public class TaskUpdateRequest {
    private String title;
    private String content;
    private Long milestoneId;
    private List<Long> tagIds;
}