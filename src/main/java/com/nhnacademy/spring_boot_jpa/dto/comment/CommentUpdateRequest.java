package com.nhnacademy.spring_boot_jpa.dto.comment;

import lombok.Data;
import lombok.NoArgsConstructor;

// 댓글 수정을 위한 DTO
@Data
@NoArgsConstructor
public class CommentUpdateRequest {
    private String content;
}