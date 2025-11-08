package com.nhnacademy.spring_boot_jpa.service;

import com.nhnacademy.spring_boot_jpa.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RestTemplate restTemplate;

    @Override
    public Users getUser(String username){
        return null;
    }

    @Override
    public List<Users> getMemberAll() {
        return List.of();
    }

    @Override
    public void deleteMember(String id) {

    }


}
