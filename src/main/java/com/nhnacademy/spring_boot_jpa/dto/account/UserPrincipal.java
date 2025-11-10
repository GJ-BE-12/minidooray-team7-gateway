package com.nhnacademy.spring_boot_jpa.dto.account;


import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

// 인증된 사용자 정보

@Getter
public class UserPrincipal extends User implements Serializable{
    private String userId;
    private String username;
    private String email;

    public UserPrincipal(String userId, String username, String password, String email, Collection<? extends GrantedAuthority> authorities) {
        // 부모 User 클래스는 username(loginId), password, authorities를 사용
        super(username, password, authorities);
        this.userId = userId; // (중요) 생성자로 받은 memberId를 'id' 필드에 저장
        this.username = username;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
