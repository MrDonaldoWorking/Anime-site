package com.donaldo.site.backend.server.security.jwt;

import com.donaldo.site.backend.server.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class jwtTest {
//    final AuthenticationManager authenticationManager = new ProviderManager(new CustomAuthenticationProvider());

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

/*
The dependencies of some of the beans in the application context form a cycle:

┌─────┐
|  serverApplication
↑     ↓
|  webSecurityConfig
↑     ↓
|  org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration$EnableWebMvcConfiguration
└─────┘

java.lang.ClassCastException: class java.lang.String cannot be cast to class com.donaldo.site.backend.server.security.services.UserDetailsImpl (java.lang.String is in module java.base of loader 'bootstrap'; com.donaldo.site.backend.server.security.services.UserDetailsImpl is in unnamed module of loader 'app')

    @Test
*/
    public void jwtTokenTest() {
        final User user = new User("user", "user");
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
        final String jwt = jwtUtils.generateJwtToken(authentication);

        assertTrue(jwtUtils.validateJwtToken(jwt));
        assertEquals(user.getUsername(), jwtUtils.getUserNameFromJwtToken(jwt));
    }
}
