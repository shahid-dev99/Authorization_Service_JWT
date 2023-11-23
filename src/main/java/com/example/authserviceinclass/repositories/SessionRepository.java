package com.example.authserviceinclass.repositories;

import com.example.authserviceinclass.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session,Long> {
    Session save(Session session);
    Optional<Session> findByTokenAndUser_Id(String token, Long id);
}
