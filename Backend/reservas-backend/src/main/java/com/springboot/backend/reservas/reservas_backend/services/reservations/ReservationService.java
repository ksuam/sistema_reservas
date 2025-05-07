package com.springboot.backend.reservas.reservas_backend.services.reservations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.springboot.backend.reservas.reservas_backend.dto.CreateReservationRequestDTO;
import com.springboot.backend.reservas.reservas_backend.dto.UpdateReservationRequestDTO;
import com.springboot.backend.reservas.reservas_backend.entities.Reservation;
import com.springboot.backend.reservas.reservas_backend.entities.Space;
import com.springboot.backend.reservas.reservas_backend.entities.User;
import com.springboot.backend.reservas.reservas_backend.repositories.reservations.ReservationRepository;
import com.springboot.backend.reservas.reservas_backend.repositories.spaces.SpaceRepository;
import com.springboot.backend.reservas.reservas_backend.repositories.users.UserRepository;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

public interface ReservationService {

    // Crea una nueva reserva, si no existe un solapamiento
    Reservation createReservation(CreateReservationRequestDTO request);

    List<Reservation> findByUserId(Long userId);

    // Verifica si ya existe un conflicto en el espacio y las fechas solicitadas
    boolean hasConflict(Long spaceId, LocalDateTime startTime, LocalDateTime endTime);

    Reservation updateReservation(Long id, UpdateReservationRequestDTO requestDTO);

    void deleteReservation(Long reservationId);

    Reservation findById(Long id);

    List<Reservation> findAllReservations();
}