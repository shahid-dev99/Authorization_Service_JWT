package com.example.authserviceinclass.security.models;

import com.example.authserviceinclass.models.Role;
import com.example.authserviceinclass.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetail implements UserDetails {
    private User user;
    public CustomUserDetail(User user){
      this. user = user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> roles = new ArrayList<>();
        for(Role role : user.getRoles()){
            roles.add(new CustomGrantedAuthority(role));
        }
        return roles;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // not handling as of now
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // can be used to mark accounts locked when multiple
                    // password failures .. hacking case
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // can be used to make user change password
    }

    @Override
    public boolean isEnabled() {
        return true; // not supported as of now
     }
}
