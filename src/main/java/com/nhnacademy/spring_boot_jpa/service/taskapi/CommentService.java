package com.nhnacademy.spring_boot_jpa.service.taskapi;

import com.nhnacademy.spring_boot_jpa.dto.comment.CommentCreateRequest;
import com.nhnacademy.spring_boot_jpa.dto.comment.CommentUpdateRequest;

public interface CommentService {
    /** 댓글을 생성 */
    void createComment(Long memberId, Long taskId, CommentCreateRequest request);

    /** 댓글 수정 */
    void updateComment(Long memberId, Long commentId, CommentUpdateRequest request);

    /** 댓글 삭제 */
    void deleteComment(Long memberId, Long commentId);
}