package com.example.authserviceinclass.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/getuserdummy")
    public String  getUser(){
        return "I am a dummy user";
    }
}
