package com.nhnacademy.spring_boot_jpa.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

// Task-Api로 댓글 생성을 요청하기 위한 DTO
@Data
@NoArgsConstructor
public class CommentCreateRequest {
    private String content; // 댓글 내용
}