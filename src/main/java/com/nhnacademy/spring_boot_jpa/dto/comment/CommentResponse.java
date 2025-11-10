package com.nhnacademy.spring_boot_jpa.dto.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

// Task-Api로부터 댓글 정보를 받기 위한 DTO
@Getter
@Setter
@NoArgsConstructor
public class CommentResponse {
    private Long commentId;
    private String content;
    private String authorName;

    // 작성 시간..
    private ZonedDateTime writeTime;
}