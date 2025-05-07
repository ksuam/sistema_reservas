package com.springboot.backend.reservas.reservas_backend.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String username;
    private String password;
}