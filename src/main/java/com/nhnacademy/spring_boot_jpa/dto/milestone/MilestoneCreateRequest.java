package com.nhnacademy.spring_boot_jpa.dto.milestone;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MilestoneCreateRequest {
    private String name;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
}