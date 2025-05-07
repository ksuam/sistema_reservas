package com.springboot.backend.reservas.reservas_backend.services.spaces;

import java.util.List;

import com.springboot.backend.reservas.reservas_backend.entities.Space;

public interface SpaceService {
    List<Space> findAll();
    Space findById(Long id);
    Space save(Space space);
    void delete(Long id);
    Space partialUpdate(Long id, Space space);
}
