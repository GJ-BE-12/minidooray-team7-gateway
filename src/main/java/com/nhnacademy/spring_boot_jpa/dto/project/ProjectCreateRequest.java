package com.nhnacademy.spring_boot_jpa.dto.project;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProjectCreateRequest {
    private String projectName;
    // 생성 시 기본 상태는 TaskApi에서 "활성"으로 처리
}