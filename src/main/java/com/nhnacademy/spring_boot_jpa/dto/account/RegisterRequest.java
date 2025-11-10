package com.nhnacademy.spring_boot_jpa.dto.account;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//회원가입 요청
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String userId;
    private String username;
    private String password;
    private String email;

}
