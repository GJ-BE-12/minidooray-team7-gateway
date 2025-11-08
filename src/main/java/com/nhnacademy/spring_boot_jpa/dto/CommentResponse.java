package com.nhnacademy.spring_boot_jpa.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

// Task-Api로부터 댓글 정보를 받기 위한 DTO
@Data
@NoArgsConstructor
public class CommentResponse {
    private Long commentId;
    private String content;
    private String authorName;

    // 작성 시간..
}