package com.nhnacademy.spring_boot_jpa.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// account-api로부터 인증정보를 받아오기위한 DTO
@Getter
@Setter
@NoArgsConstructor
public class UserAuthResponse {
    private String userId;
    private String username;
    private String password; // 암호화된 비밀번호
    private String email;
    private String authority; // ex) "ROLE_USER"
}
