package com.springboot.backend.reservas.reservas_backend.services.reservations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.backend.reservas.reservas_backend.dto.CreateReservationRequestDTO;
import com.springboot.backend.reservas.reservas_backend.dto.UpdateReservationRequestDTO;
import com.springboot.backend.reservas.reservas_backend.entities.Reservation;
import com.springboot.backend.reservas.reservas_backend.entities.Space;
import com.springboot.backend.reservas.reservas_backend.entities.User;
import com.springboot.backend.reservas.reservas_backend.repositories.reservations.ReservationRepository;
import com.springboot.backend.reservas.reservas_backend.repositories.spaces.SpaceRepository;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final SpaceRepository spaceRepository;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository, SpaceRepository spaceRepository) {
        this.reservationRepository = reservationRepository;
        this.spaceRepository = spaceRepository;
    }

    @Override
    public Reservation createReservation(CreateReservationRequestDTO request) {
        Long userId = request.getUserId();
        Long spaceId = request.getSpaceId();
        LocalDateTime startTime = request.getStartTime();
        LocalDateTime endTime = request.getEndTime();

        // Verificar si existe conflicto
        if (hasConflict(spaceId, startTime, endTime)) {
            throw new RuntimeException("El espacio ya está reservado en el horario seleccionado");
        }

        // Crear nueva reserva
        Reservation reservation = Reservation.builder()
                .user(new User(userId))
                .space(new Space(spaceId))
                .startTime(startTime)
                .endTime(endTime)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> findByUserId(Long userId) {

        return reservationRepository.findAllByUserId(userId);
    }

    @Override
    public boolean hasConflict(Long spaceId, LocalDateTime startTime, LocalDateTime endTime) {
        // Verificar si existe alguna reserva que se solape en el espacio
        List<Reservation> overlappingReservations = reservationRepository.findOverlappingReservations(spaceId,
                startTime, endTime);
        return !overlappingReservations.isEmpty(); // Si hay reservas solapadas, hay conflicto
    }

    @Override
    public Reservation updateReservation(Long id, UpdateReservationRequestDTO requestDTO) {
        Reservation existing = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        if (requestDTO.getSpaceId() != null) {
            existing.setSpace(new Space(requestDTO.getSpaceId()));
        }

        if (requestDTO.getStartTime() != null) {
            existing.setStartTime(requestDTO.getStartTime());
        }

        if (requestDTO.getEndTime() != null) {
            existing.setEndTime(requestDTO.getEndTime());
        }

        existing.setUpdatedAt(LocalDateTime.now());

        // Validar conflictos con nueva info
        if (hasConflict(existing.getSpace().getId(), existing.getStartTime(), existing.getEndTime())) {
            throw new RuntimeException("El espacio ya está reservado en el horario seleccionado");
        }

        return reservationRepository.save(existing);
    }

    @Override
    public void deleteReservation(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    @Override
    public Reservation findById(Long id) {
        Optional<Reservation> optionalReserva = reservationRepository.findById(id);

        // Si no se encuentra la reserva, retornamos null
        return optionalReserva.orElse(null);
    }

    @Override
    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }
}