package com.springboot.backend.reservas.reservas_backend.repositories.reservations;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.backend.reservas.reservas_backend.entities.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    
    // Método para obtener todas las reservas de un usuario
    List<Reservation> findAllByUserId(Long userId);



    // Verifica si ya existe una reserva en el mismo espacio
    @Query("SELECT r FROM Reservation r WHERE r.space.id = :spaceId " +
            "AND ((r.startTime BETWEEN :startTime AND :endTime) OR " +
            "(r.endTime BETWEEN :startTime AND :endTime) OR " +
            "(r.startTime <= :startTime AND r.endTime >= :endTime))")
    List<Reservation> findOverlappingReservations(@Param("spaceId") Long spaceId,
                                                        @Param("startTime") LocalDateTime startTime,
                                                        @Param("endTime") LocalDateTime endTime);
}