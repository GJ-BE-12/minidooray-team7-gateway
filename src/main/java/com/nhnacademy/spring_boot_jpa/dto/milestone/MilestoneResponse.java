package com.nhnacademy.spring_boot_jpa.dto.milestone;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MilestoneResponse {
    private Long milestoneId;
    private String name;
    private ZonedDateTime startDate; // ERD에는 없지만, 마일스톤에 일반적으로 필요하다고 함...
    private ZonedDateTime endDate;
}