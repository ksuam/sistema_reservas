package com.springboot.backend.reservas.reservas_backend.dto;

import java.time.LocalDateTime;

import org.antlr.v4.runtime.misc.NotNull;

import lombok.Data;
@Data
public class CreateReservationRequestDTO {
    private Long userId;
    private Long spaceId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
