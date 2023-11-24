package com.example.authserviceinclass.security.userDetailsrv;

import com.example.authserviceinclass.models.User;
import com.example.authserviceinclass.repositories.UserRepository;
import com.example.authserviceinclass.security.models.CustomUserDetail;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomeUserDetail implements UserDetailsService {
    private UserRepository userRepository;
    public CustomeUserDetail(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("user with email "+ username + " not found");
        }
        return new CustomUserDetail(user.get());
    }
}
