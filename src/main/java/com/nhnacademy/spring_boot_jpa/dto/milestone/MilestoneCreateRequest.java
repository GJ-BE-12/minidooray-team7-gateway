package com.nhnacademy.spring_boot_jpa.dto.milestone;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
public class MilestoneCreateRequest {
    private String milestoneName;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
}