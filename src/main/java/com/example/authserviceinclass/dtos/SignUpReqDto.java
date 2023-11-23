package com.example.authserviceinclass.dtos;

import jakarta.persistence.GeneratedValue;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpReqDto {
    private String email;
    private String password;
}
