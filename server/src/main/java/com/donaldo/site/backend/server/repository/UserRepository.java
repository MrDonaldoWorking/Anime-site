package com.donaldo.site.backend.server.repository;

import com.donaldo.site.backend.server.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(final String username);

    Boolean existsByUsername(final String username);

    Optional<User> findById(final long id);
}
