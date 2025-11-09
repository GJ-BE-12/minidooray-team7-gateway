package com.nhnacademy.spring_boot_jpa.dto.project;

import lombok.Data;
import lombok.NoArgsConstructor;

// AccountApi 또는 TaskApi로부터 프로젝트 멤버 정보를 받기 위한 DTO
@Data
@NoArgsConstructor
public class ProjectMemberResponse {
    private Long memberId;
    private String username;
    private String email;
}