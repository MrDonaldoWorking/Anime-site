package com.donaldo.site.backend.server.controllers;

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
import com.donaldo.site.backend.server.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/spring/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.authUser(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        final MessageResponse response = authService.registerUser(signupRequest);
        if (response.getMessage().endsWith("already exists")) {
            return ResponseEntity.badRequest().body(response);
        } else {
            return ResponseEntity.ok(response);
        }
    }
}
