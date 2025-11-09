package com.nhnacademy.spring_boot_jpa.service.taskapi;

import com.nhnacademy.spring_boot_jpa.dto.project.*;
import java.util.List;

public interface ProjectService {
    /** 프로젝트 목록을 조회 */
    List<ProjectResponse> getMyProjects(Long memberId);

    /** 프로젝트 상세 정보를 조회 */
    ProjectDetailsResponse getProjectDetails(Long memberId, Long projectId);

    /** 프로젝트 생성 */
    void createProject(Long memberId, ProjectCreateRequest request);

    /** 프로젝트 수정 */
    void updateProject(Long memberId, Long projectId, ProjectUpdateRequest request);

    /** 프로젝트 멤버 목록 조회 */
    List<ProjectMemberResponse> getProjectMembers(Long memberId, Long projectId);

    /** 프로젝트 멤버 추가 */
    void addProjectMember(Long memberId, Long projectId, String username);

    /** 프로젝트 멤버 삭제 */
    void deleteProjectMember(Long memberId, Long projectId, Long memberIdToRemove);
}