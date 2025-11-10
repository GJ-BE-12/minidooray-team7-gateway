package com.nhnacademy.spring_boot_jpa.dto.project;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProjectUpdateRequest {
    private String projectName;
    private String projectStatus; // "활성", "휴면", "종료"
}