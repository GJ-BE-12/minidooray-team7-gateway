package com.nhnacademy.spring_boot_jpa.service.taskapi;

import com.nhnacademy.spring_boot_jpa.dto.milestone.MilestoneCreateRequest;
import com.nhnacademy.spring_boot_jpa.dto.milestone.MilestoneResponse;
import com.nhnacademy.spring_boot_jpa.dto.milestone.MilestoneUpdateRequest;
import java.util.List;

public interface MilestoneService {
    /** 프로젝트의 마일스톤 목록 조회 */
    List<MilestoneResponse> getMilestones(Long memberId, Long projectId);

    /** 마일스톤 생성 */
    void createMilestone(Long memberId, Long projectId, MilestoneCreateRequest request);

    /** 마일스톤 수정 */
    void updateMilestone(Long memberId, Long projectId, Long milestoneId, MilestoneUpdateRequest request);

    /** 마일스톤 삭제 */
    void deleteMilestone(Long memberId, Long projectId, Long milestoneId);
}