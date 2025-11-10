package com.nhnacademy.spring_boot_jpa.dto.task;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TaskResponse {
    private long taskId;
    private String title;
    private String body;
}
