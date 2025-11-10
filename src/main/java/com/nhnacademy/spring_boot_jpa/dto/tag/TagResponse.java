package com.nhnacademy.spring_boot_jpa.dto.tag;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TagResponse {
    private Long tagId;
    private String name;
}
