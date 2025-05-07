package com.springboot.backend.reservas.reservas_backend.controllers.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.backend.reservas.reservas_backend.controllers.ExceptionControl;
import com.springboot.backend.reservas.reservas_backend.dto.LoginRequestDTO;
import com.springboot.backend.reservas.reservas_backend.entities.User;
import com.springboot.backend.reservas.reservas_backend.services.users.UserServiceImpl;
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        Optional<User> userOpt = userServiceImpl.findByUsernameAndPassword(
                request.getUsername(), request.getPassword()
        );

        if (userOpt.isPresent()) {
            return ResponseEntity.ok(userOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ExceptionControl("404", "Usuario no encontrado o credenciales incorrectas"));
        }
    }
}