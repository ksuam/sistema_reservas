package com.springboot.backend.reservas.reservas_backend.services.users;

import com.springboot.backend.reservas.reservas_backend.entities.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {

    List<User> findAllUsers();

    Optional<User> findById(Long id);

    User save(User user);

    User patch(Long id, Map<String, Object> updates);

    void delete(Long id);

    Optional<User> findByUsernameAndPassword(String username, String password);
}
