package com.nhnacademy.spring_boot_jpa.dto.project;

import com.nhnacademy.spring_boot_jpa.dto.milestone.MilestoneResponse;
import com.nhnacademy.spring_boot_jpa.dto.tag.TagResponse;
import com.nhnacademy.spring_boot_jpa.dto.task.TaskResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProjectDetailsResponse {
    private long projectId;
    private String projectName;
    private String projectStatus;
    private List<TaskResponse> tasks;

    // 프로젝트에 속한 태그 및 마일스톤 목록
    private List<TagResponse> projectTags;
    private List<MilestoneResponse> projectMilestones;
}
