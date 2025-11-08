package com.nhnacademy.spring_boot_jpa.dto.account;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 로그인 요청
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    private String id;
    private String password;

}
