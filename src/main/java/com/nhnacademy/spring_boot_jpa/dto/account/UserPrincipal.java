package com.nhnacademy.spring_boot_jpa.dto.account;


import lombok.Data;
import lombok.Getter;

// 인증된 사용자 정보
@Data
@Getter
public class UserPrincipal {
    private Long id;
    private String username;
    private String email;
}
