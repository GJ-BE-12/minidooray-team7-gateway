package com.nhnacademy.spring_boot_jpa.dto.account;


import lombok.Data;

//회원가입 요청
@Data
public class RegisterRequest {
    private String userId;
    private String username;
    private String password;
    private String email;
}
