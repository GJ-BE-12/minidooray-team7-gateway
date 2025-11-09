package com.nhnacademy.spring_boot_jpa.service.taskapi;

import com.nhnacademy.spring_boot_jpa.dto.tag.TagCreateRequest;
import com.nhnacademy.spring_boot_jpa.dto.tag.TagResponse;
import com.nhnacademy.spring_boot_jpa.dto.tag.TagUpdateRequest;
import java.util.List;

public interface TagService {
    /** 프로젝트의 태그 목록 조회 */
    List<TagResponse> getTags(String userId, Long projectId);

    /** 태그 생성 */
    void createTag(String userId, Long projectId, TagCreateRequest request);

    /** 태그 수정 */
    void updateTag(String userId, Long projectId, Long tagId, TagUpdateRequest request);

    /** 태그 삭제 */
    void deleteTag(String userId, Long projectId, Long tagId);
}