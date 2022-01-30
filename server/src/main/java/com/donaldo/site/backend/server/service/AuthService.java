package com.donaldo.site.backend.server.service;

import com.donaldo.site.backend.server.models.ERole;
import com.donaldo.site.backend.server.models.Role;
import com.donaldo.site.backend.server.models.User;
import com.donaldo.site.backend.server.payload.request.LoginRequest;
import com.donaldo.site.backend.server.payload.request.SignupRequest;
import com.donaldo.site.backend.server.payload.response.JwtResponse;
import com.donaldo.site.backend.server.payload.response.MessageResponse;
import com.donaldo.site.backend.server.repository.RoleRepository;
import com.donaldo.site.backend.server.repository.UserRepository;
import com.donaldo.site.backend.server.security.jwt.JwtUtils;
import com.donaldo.site.backend.server.security.services.UserDetailsImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {
    final AuthenticationManager authenticationManager;
    final UserRepository userRepository;
    final RoleRepository roleRepository;
    final PasswordEncoder encoder;
    final JwtUtils jwtUtils;

    public AuthService(final AuthenticationManager authenticationManager,
                          final UserRepository userRepository, final RoleRepository roleRepository,
                          final PasswordEncoder encoder, final JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    public JwtResponse authUser(final LoginRequest loginRequest) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String jwt = jwtUtils.generateJwtToken(authentication);

        final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        final List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        System.out.println("Logged in user: " + loginRequest.getUsername());
        return new JwtResponse(jwt,
                userDetails.getId(), userDetails.getUsername(),
                roles);
    }

    public MessageResponse registerUser(final SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return new MessageResponse("User with name '" + signupRequest.getUsername() + "' is already exists");
        }

        // create new account
        final User user = new User(signupRequest.getUsername(), encoder.encode(signupRequest.getPassword()));
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
        return new MessageResponse("User registered!");
    }
}
