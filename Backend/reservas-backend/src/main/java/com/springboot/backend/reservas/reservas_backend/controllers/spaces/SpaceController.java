package com.springboot.backend.reservas.reservas_backend.controllers.spaces;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.backend.reservas.reservas_backend.entities.Space;
import com.springboot.backend.reservas.reservas_backend.services.spaces.SpaceService;

@RestController
@RequestMapping("/api/spaces")
@CrossOrigin(origins = "*")
public class SpaceController {

    @Autowired
    private SpaceService spaceService;

    @GetMapping
    public List<Space> getAllSpaces() {
        return spaceService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Space> getSpaceById(@PathVariable Long id) {
        Space space = spaceService.findById(id);
        return space != null ? ResponseEntity.ok(space) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Space> createSpace(@RequestBody Space space) {
        return ResponseEntity.ok(spaceService.save(space));
    }

@PatchMapping("/{id}")
public ResponseEntity<Space> partialUpdateSpace(@PathVariable Long id, @RequestBody Space space) {
    Space updated = spaceService.partialUpdate(id, space);
    return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpace(@PathVariable Long id) {
        spaceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
