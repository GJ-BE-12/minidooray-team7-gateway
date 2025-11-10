package com.nhnacademy.spring_boot_jpa.service.taskapi;

import com.nhnacademy.spring_boot_jpa.dto.project.*;
import java.util.List;

public interface ProjectService {
    /** 프로젝트 목록을 조회 */
    List<ProjectResponse> getMyProjects(String userId);

    /** 프로젝트 상세 정보를 조회 */
    ProjectDetailsResponse getProjectDetails(String userId, Long projectId);

    /** 프로젝트 생성 */
    void createProject(ProjectCreateRequest request);

    /** 프로젝트 수정 */
//    void updateProject(String userId, Long projectId, ProjectUpdateRequest request);

    /** 프로젝트 삭제 */
//    void deleteProject(String userId, Long projectId);

    // --------------------------

    /** 프로젝트 멤버 목록 조회 */
    List<ProjectMemberResponse> getProjectMembers(String userId, Long projectId);

    /** 프로젝트 멤버 추가 */
    void addProjectMember(String adminUserId, Long projectId, String memberUsername);

    /** 프로젝트 멤버 삭제 */
    void deleteProjectMember(String userId, Long projectId, String memberIdToRemove);
}