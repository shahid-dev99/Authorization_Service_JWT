package com.example.authserviceinclass.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidatedLoginReq {
    private Long userId;  // with JWT token will be unique and user id won't be
                            //needed
    private String token;
}
