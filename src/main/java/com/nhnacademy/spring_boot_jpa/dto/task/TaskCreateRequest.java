package com.nhnacademy.spring_boot_jpa.dto.task;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TaskCreateRequest {
    private String title;
    private String body;

    private Long milestoneId;
    private List<Long> tagIds;
}