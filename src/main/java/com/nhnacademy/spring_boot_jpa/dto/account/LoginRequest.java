package com.nhnacademy.spring_boot_jpa.dto.account;


import lombok.Data;

// 로그인 요청
@Data

public class LoginRequest {

    private String id;
    private String password;

}
