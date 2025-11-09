package com.nhnacademy.spring_boot_jpa.dto.milestone;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
public class MilestoneResponse {
    private Long milestoneId;
    private String milestoneName;
    private ZonedDateTime startDate; // ERD에는 없지만, 마일스톤에 일반적으로 필요하다고 함...
    private ZonedDateTime endDate;
}