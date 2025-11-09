package com.nhnacademy.spring_boot_jpa.dto.comment;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

// Task-Api로부터 댓글 정보를 받기 위한 DTO
@Data
@NoArgsConstructor
public class CommentResponse {
    private Long commentId;
    private String content;
    private String authorName;

    // 작성 시간..
    private ZonedDateTime writeTime;
}