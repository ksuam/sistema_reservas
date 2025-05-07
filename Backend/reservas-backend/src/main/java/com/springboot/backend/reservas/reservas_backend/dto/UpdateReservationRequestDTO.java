package com.springboot.backend.reservas.reservas_backend.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UpdateReservationRequestDTO {
    private Long spaceId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}