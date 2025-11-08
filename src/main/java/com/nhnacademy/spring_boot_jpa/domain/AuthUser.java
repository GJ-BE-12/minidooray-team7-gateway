//package com.nhnacademy.spring_boot_jpa.domain;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.List;
//
//
//
//public class AuthUser implements UserDetails {
//
//    private Users user;
//
//    public AuthUser(Users user) {
//        this.user=user;
//
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of();
//    }
//
//    @Override
//    public String getPassword() {
//        return user.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return user.getUser_name();
//    }
//}
