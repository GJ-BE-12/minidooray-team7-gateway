package com.nhnacademy.spring_boot_jpa.service.taskapi;

import com.nhnacademy.spring_boot_jpa.dto.task.TaskCreateRequest;
import com.nhnacademy.spring_boot_jpa.dto.task.TaskDetailsResponse;
import com.nhnacademy.spring_boot_jpa.dto.task.TaskUpdateRequest;

public interface TaskService {
    /** 태스크 상세 정보 조회 */
    TaskDetailsResponse getTaskDetails(String userId, Long taskId);

    /** 태스크 생성 */
    void createTask(String userId, Long projectId, TaskCreateRequest request);

    /** 태스크 수정 */
    void updateTask(String userId, Long taskId, TaskUpdateRequest request);

    /** 태스크 삭제 */
    void deleteTask(String userId, Long taskId);
}