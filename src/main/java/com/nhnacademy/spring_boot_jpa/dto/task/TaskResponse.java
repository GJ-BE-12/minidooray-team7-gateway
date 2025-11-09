package com.nhnacademy.spring_boot_jpa.dto.task;

import lombok.Data;

@Data
public class TaskResponse {
    private long taskId;
    private String title;
    private String body;
}
