package com.nhnacademy.spring_boot_jpa.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProjectDetailsResponse {
    private long projectId;
    private String projectName;
    private String projectStatus;
    private List<TaskResponse> tasks; // 프로젝트에 속한 태스크 목록


    // 멤버, 태그, 마일스톤...
}
