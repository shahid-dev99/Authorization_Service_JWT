package com.example.authserviceinclass.security.models;

import com.example.authserviceinclass.models.Role;
import org.springframework.security.core.GrantedAuthority;

public class CustomGrantedAuthority implements GrantedAuthority {
    private Role role;
    public CustomGrantedAuthority(Role role){
        this.role = role;
    }
    @Override
    public String getAuthority() {
        return role.getRole();
    }
}
