package com.example.authserviceinclass.controllers;

import com.example.authserviceinclass.dtos.*;
import com.example.authserviceinclass.exceptions.InvalidPassword_ex;
import com.example.authserviceinclass.exceptions.UserAlreadyExisting;
import com.example.authserviceinclass.exceptions.UserDoesNotExist_ex;
import com.example.authserviceinclass.models.SessionStatus;
import com.example.authserviceinclass.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService service;

    public AuthController(AuthService service){
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginReqDto reqDto) throws UserDoesNotExist_ex, InvalidPassword_ex {

        return service.login(reqDto.getEmail(),reqDto.getPassword());

    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutReqDto reqDto){
        return service.logout(reqDto.getToken(),reqDto.getUserId());

    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignUpReqDto reqDto) throws UserAlreadyExisting {
        UserDto userDto = service.signUp(reqDto.getEmail(),reqDto.getPassword());
        return  new ResponseEntity<>(userDto, HttpStatus.OK);

    }
    @PostMapping("/validate")
    public SessionStatus validate(@RequestBody ValidatedLoginReq reqDto){
        return service.validateRequest(reqDto.getToken(),reqDto.getUserId()) ;
    }

}
