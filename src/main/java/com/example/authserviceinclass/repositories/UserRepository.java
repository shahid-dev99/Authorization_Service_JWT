package com.example.authserviceinclass.repositories;

import com.example.authserviceinclass.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

     Optional<User> findByEmail(String email);
   User save(User user);
}
