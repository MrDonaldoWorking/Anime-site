package com.donaldo.site.backend.server.controllers;

import com.donaldo.site.backend.server.models.User;
import com.donaldo.site.backend.server.payload.request.LoginRequest;
import com.donaldo.site.backend.server.payload.request.SignupRequest;
import com.donaldo.site.backend.server.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/spring/auth")
public class AuthController {
    final AuthService service;

    public AuthController(final AuthService service) {
        this.service = service;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authUser(@Valid @RequestBody LoginRequest loginRequest) {
        final User user = service.authUser(loginRequest);
        if (user == null) {
            return ResponseEntity.badRequest().body("Cannot find user with username = " + loginRequest.getUsername());
        } else {
            return ResponseEntity.ok(user);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (service.registerUser(signupRequest)) {
            return ResponseEntity.ok("Successfully registered!");
        } else {
            return ResponseEntity.badRequest().body("User with username " + signupRequest.getUsername() + " already exists");
        }
    }
}
