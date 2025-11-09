package com.nhnacademy.spring_boot_jpa.dto.tag;

import lombok.Data;
import lombok.NoArgsConstructor;

// 태그 수정을 위한 DTO
@Data
@NoArgsConstructor
public class TagUpdateRequest {
    private String tagName;
}