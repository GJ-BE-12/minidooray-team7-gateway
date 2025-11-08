package com.nhnacademy.spring_boot_jpa.service;

import com.nhnacademy.spring_boot_jpa.domain.Users;

import java.util.List;

public interface UserService {
    Users getUser(String username);
    List<Users> getMemberAll();
    void deleteMember(String id);
}
