package com.example.authserviceinclass.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogoutReqDto {
    private Long userId;
    private String token;
}
