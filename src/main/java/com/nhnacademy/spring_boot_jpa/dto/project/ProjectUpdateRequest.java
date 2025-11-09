package com.nhnacademy.spring_boot_jpa.dto.project;

import lombok.Data;
import lombok.NoArgsConstructor;

// 프로젝트 정보(이름, 상태) 수정을 위한 DTO
@Data
@NoArgsConstructor
public class ProjectUpdateRequest {
    private String projectName;
    private String projectStatus; // "활성", "휴면", "종료"
}