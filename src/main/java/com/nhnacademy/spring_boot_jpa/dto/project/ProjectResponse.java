package com.nhnacademy.spring_boot_jpa.dto.project;

import lombok.Data;
import lombok.NoArgsConstructor;

// task-api 와 RestTemplate으로 통신할 때 사용할 DTO
// >> 프로젝트 목록 조회용
@Data
@NoArgsConstructor
public class ProjectResponse {
    private long projectId;
    private String projectName;
    private String projectStatus; // Enum 타입 -> String으로 변환해서 받을 수 있는지...
}
