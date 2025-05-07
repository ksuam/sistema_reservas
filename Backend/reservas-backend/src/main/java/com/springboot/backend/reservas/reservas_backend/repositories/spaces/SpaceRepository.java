package com.springboot.backend.reservas.reservas_backend.repositories.spaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.backend.reservas.reservas_backend.entities.Space;

@Repository
public interface SpaceRepository extends JpaRepository<Space, Long> {
}
