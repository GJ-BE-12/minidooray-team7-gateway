package com.nhnacademy.spring_boot_jpa.dto.tag;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TagCreateRequest {
    private String tagName;
}