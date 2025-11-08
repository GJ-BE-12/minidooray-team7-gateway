package com.nhnacademy.spring_boot_jpa.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

// 회원가입 폼(4단계) 데이터를 Account-Api로 보내기 위한 DTO
@Data
@NoArgsConstructor
public class UserCreateCommand {
    private String username;
    private String password;
    private String email;
    // Account-Api에서 요구하는 추가 필드가 있다면 여기에 추가
}