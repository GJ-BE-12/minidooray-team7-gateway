package com.nhnacademy.spring_boot_jpa.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

// Account-Api (3단계)로부터 인증 정보를 받기 위한 DTO
@Data
@NoArgsConstructor
public class UserAuthResponse {
    private String username;
    private String password;  // 암호화된 비밀번호
    private String authority; // 권한 (예: "ROLE_USER")
}