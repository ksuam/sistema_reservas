package com.springboot.backend.reservas.reservas_backend.services.spaces;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.backend.reservas.reservas_backend.entities.Space;
import com.springboot.backend.reservas.reservas_backend.repositories.spaces.SpaceRepository;

@Service
public class SpaceServiceImpl implements SpaceService {

    @Autowired
    private SpaceRepository spaceRepository;

    @Override
    public List<Space> findAll() {
        return spaceRepository.findAll();
    }

    @Override
    public Space findById(Long id) {
        return spaceRepository.findById(id).orElse(null);
    }

    @Override
    public Space save(Space space) {
        return spaceRepository.save(space);
    }

    @Override
    public Space partialUpdate(Long id, Space space) {
        Optional<Space> optional = spaceRepository.findById(id);
        if (optional.isEmpty())
            return null;

        Space existing = optional.get();

        if (space.getName() != null) {
            existing.setName(space.getName());
        }

        if (space.getDescription() != null) {
            existing.setDescription(space.getDescription());
        }

        return spaceRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        spaceRepository.deleteById(id);
    }
}
