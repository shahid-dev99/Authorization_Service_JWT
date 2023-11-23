package com.example.authserviceinclass.exceptions;

public class UserAlreadyExisting extends Exception{
    public UserAlreadyExisting(String message ){
        super(message);
    }
}
