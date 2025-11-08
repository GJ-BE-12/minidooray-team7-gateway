package com.nhnacademy.spring_boot_jpa.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

// task-api로 태스크 생성 요청을 위한 dto
@Data
@NoArgsConstructor
public class TaskCreateRequest {
    private String title;
    private String content;

    // milestone id, tag id ..
}
