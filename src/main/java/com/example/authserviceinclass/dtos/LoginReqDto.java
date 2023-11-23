package com.example.authserviceinclass.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginReqDto {
//    private Long userid;
    private String email;
    private String password;
}
