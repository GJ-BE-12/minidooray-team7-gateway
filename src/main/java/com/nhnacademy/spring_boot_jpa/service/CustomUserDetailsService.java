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
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        log.info("Attempting to authenticate user: {}", userId);

        UserAuthResponse userResponse = accountApiClient.getUserAuthDetails(userId);

        if (userResponse == null || userResponse.getPassword() == null) {
            log.warn("User not found or password null from API: {}", userId);
            throw new UsernameNotFoundException("User not found or invalid response: " + userId);
        }

        log.info("User found from API: {}. (MemberId: {})", userId, userResponse.getUserId());

        return new UserPrincipal(
                userResponse.getUserId(),
                userResponse.getUsername(),
                userResponse.getPassword(), // API가 암호화된 비밀번호를 반환해야 함
                userResponse.getEmail(),
                Collections.singletonList(new SimpleGrantedAuthority(userResponse.getAuthority()))
        );
    }
}