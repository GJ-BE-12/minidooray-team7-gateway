package com.nhnacademy.spring_boot_jpa.dto.project;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

// AccountApi 또는 TaskApi로부터 프로젝트 멤버 정보를 받기 위한 DTO
@Data
@NoArgsConstructor
public class ProjectMemberResponse {
//    private String memberId;
//    private String userId;
//    private String email;
    private long projectMemberId;
    private String userId;
    private long projectId;
    private ZonedDateTime joinAt;
}