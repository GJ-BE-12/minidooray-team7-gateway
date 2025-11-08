package com.nhnacademy.spring_boot_jpa.service;

import com.nhnacademy.spring_boot_jpa.dto.UserAuthResponse;
import com.nhnacademy.spring_boot_jpa.dto.account.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountApiClient accountApiClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Attempting to authenticate user: {}", username);

        UserAuthResponse userResponse = accountApiClient.getUserAuthDetails(username);

        if (userResponse == null || userResponse.getPassword() == null) {
            log.warn("User not found or password null from API: {}", username);
            throw new UsernameNotFoundException("User not found or invalid response: " + username);
        }

        log.info("User found from API: {}. (MemberId: {})", username, userResponse.getMemberId());

        return new UserPrincipal(
                userResponse.getMemberId(),
                userResponse.getUsername(),
                userResponse.getPassword(), // API가 암호화된 비밀번호를 반환해야 함
                userResponse.getEmail(),
                Collections.singletonList(new SimpleGrantedAuthority(userResponse.getAuthority()))
        );
    }
}