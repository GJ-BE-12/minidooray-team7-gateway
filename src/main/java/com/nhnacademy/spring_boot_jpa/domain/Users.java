package com.nhnacademy.spring_boot_jpa.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
public class Users {
    @Id
    private Integer user_id;
    private String password;
    private String user_name;
    private String email;
    private LocalDateTime created_at;


}
