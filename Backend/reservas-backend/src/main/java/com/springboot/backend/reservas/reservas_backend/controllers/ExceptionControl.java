package com.springboot.backend.reservas.reservas_backend.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionControl {
    private String code;
    private String message;
}