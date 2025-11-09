package com.nhnacademy.spring_boot_jpa.dto.milestone;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.ZonedDateTime;

// 마일스톤 수정을 위한 DTO
@Data
@NoArgsConstructor
public class MilestoneUpdateRequest {
    private String milestoneName;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
}