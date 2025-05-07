package com.springboot.backend.reservas.reservas_backend.controllers.reservations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.backend.reservas.reservas_backend.controllers.ExceptionControl;
import com.springboot.backend.reservas.reservas_backend.dto.CreateReservationRequestDTO;
import com.springboot.backend.reservas.reservas_backend.dto.UpdateReservationRequestDTO;
import com.springboot.backend.reservas.reservas_backend.entities.Reservation;
import com.springboot.backend.reservas.reservas_backend.services.reservations.ReservationService;
import com.springboot.backend.reservas.reservas_backend.services.reservations.ReservationServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reserva")
public class ReservationController {

    @Autowired
    private ReservationServiceImpl reservationServiceImpl;

    @GetMapping("/todas")
public ResponseEntity<?> listarTodasLasReservas() {
    try {
        List<Reservation> reservas = reservationServiceImpl.findAllReservations();
        return ResponseEntity.ok(reservas);
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(new ExceptionControl(
                String.valueOf(HttpStatus.BAD_REQUEST.value()),
                e.getMessage()
        ));
    }
}

    @PostMapping("/crear")
    public ResponseEntity<?> crearReserva(@RequestBody CreateReservationRequestDTO request) {
        try {
            // Llamamos al servicio con el DTO
            Reservation reserva = reservationServiceImpl.createReservation(request);
            return ResponseEntity.ok(reserva);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ExceptionControl(
                    String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    e.getMessage()));
        }
    }

    @GetMapping("/usuario/{userId}")
    public ResponseEntity<?> listarReservasPorUsuario(@PathVariable Long userId) {
        try {
            List<Reservation> reservas = reservationServiceImpl.findByUserId(userId);
            return ResponseEntity.ok(reservas);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ExceptionControl(
                    String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    e.getMessage()));
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarReserva(@PathVariable Long id) {
        try {
            reservationServiceImpl.deleteReservation(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ExceptionControl(
                    String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    e.getMessage()));
        }
    }

    @PatchMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarReserva(
            @PathVariable Long id,
            @RequestBody UpdateReservationRequestDTO requestDTO) {
        try {
            Reservation actualizada = reservationServiceImpl.updateReservation(id, requestDTO);
            return ResponseEntity.ok(actualizada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ExceptionControl(
                    String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    e.getMessage()));
        }
    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity<?> obtenerReservaPorId(@PathVariable Long id) {
        try {
            // Llamamos al servicio para obtener la reserva
            Reservation reserva = reservationServiceImpl.findById(id);

            // Si no se encuentra la reserva, retornamos un error 404
            if (reserva == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ExceptionControl(
                                String.valueOf(HttpStatus.NOT_FOUND.value()),
                                "Reserva no encontrada"));
            }

            // Si la reserva se encuentra, retornamos la respuesta con los detalles
            return ResponseEntity.ok(reserva);
        } catch (Exception e) {
            // Si ocurre un error, retornamos una respuesta de error
            return ResponseEntity.badRequest().body(new ExceptionControl(
                    String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    e.getMessage()));
        }
    }
}