package com.example.authserviceinclass.dtos;

import com.example.authserviceinclass.models.Role;
import com.example.authserviceinclass.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserDto {
    private String email;
    private String token;
    private Set<Role> roles = new HashSet<>();

    public static  UserDto userToDto(User user){
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());
        return userDto;

    }
}
