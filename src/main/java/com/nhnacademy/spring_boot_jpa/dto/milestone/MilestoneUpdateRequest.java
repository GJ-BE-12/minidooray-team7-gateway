package com.nhnacademy.spring_boot_jpa.dto.milestone;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.ZonedDateTime;

// 마일스톤 수정을 위한 DTO
@Getter
@Setter
@NoArgsConstructor
public class MilestoneUpdateRequest {
    private String name;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
}