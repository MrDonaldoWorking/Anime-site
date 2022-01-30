package com.donaldo.site.backend.server.service;

import com.donaldo.site.backend.server.models.ERole;
import com.donaldo.site.backend.server.models.Role;
import com.donaldo.site.backend.server.models.User;
import com.donaldo.site.backend.server.payload.request.LoginRequest;
import com.donaldo.site.backend.server.payload.request.SignupRequest;
import com.donaldo.site.backend.server.repository.RoleRepository;
import com.donaldo.site.backend.server.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {
    final UserRepository userRepository;
    final RoleRepository roleRepository;

    public AuthService(final UserRepository userRepository, final RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User authUser(final LoginRequest loginRequest) {
        final User user = userRepository.findByUsername(loginRequest.getUsername()).orElse(null);
        if (user != null && loginRequest.getPassword().equals(user.getPassword())) {
            return user;
        }
        return null;
    }

    public boolean registerUser(final SignupRequest signupRequest) {
        final User user = new User(signupRequest.getUsername(), signupRequest.getPassword());
        if (userRepository.existsByUsername(user.getUsername())) {
            return false;
        }

        final Set<String> getRoles = signupRequest.getRole();
        final Set<Role> roles = new HashSet<>();
        if (getRoles == null) {
            final Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("ROLE_USER couldn't be found"));
            roles.add(userRole);
        } else {
            getRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        final Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("ROLE_ADMIN couldn't be found"));
                        roles.add(adminRole);

                    case "user":
                        final Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("ROLE_USER couldn't be found"));
                        roles.add(userRole);

                    default:
                        throw new RuntimeException("Unexpected role " + role);
                }
            });
        }
        user.setRoles(roles);

        userRepository.save(user);
        return true;
    }
}
