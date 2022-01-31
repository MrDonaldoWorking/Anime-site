package com.donaldo.site.backend.server.repository;

import com.donaldo.site.backend.server.models.ERole;
import com.donaldo.site.backend.server.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
