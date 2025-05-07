package com.springboot.backend.reservas.reservas_backend.repositories.users;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.backend.reservas.reservas_backend.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();
    boolean existsByEmail(String email);
}