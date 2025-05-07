package com.springboot.backend.reservas.reservas_backend.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;  // Campo para el nombre de usuario

    @Column(nullable = false, unique = true)
    private String username;  // Campo para el nombre de usuario

    @Column(nullable = false)
    private String password;  // Campo para la contraseña

    @Column(nullable = false, unique = true)
    private String email;     // Campo para el correo electrónico

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt; // Fecha de creación

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // Fecha de actualización
    
    // Constructor con id para facilitar el uso en el servicio y controller
    public User(Long id) {
        this.id = id;
    }
}
