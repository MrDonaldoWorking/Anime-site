package com.donaldo.site.backend.server.security.services;

import com.donaldo.site.backend.server.models.ERole;
import com.donaldo.site.backend.server.models.Role;
import com.donaldo.site.backend.server.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDetailsImplTest {
    @Test
    public void buildTest() {
        final User user = new User("user", "user");
        final Set<Role> roles = Arrays.stream(ERole.values()).map(Role::new).collect(Collectors.toSet());
        user.setRoles(roles);

        final UserDetailsImpl userDetails = UserDetailsImpl.build(user);

        assertEquals(user.getId(), userDetails.getId());
        assertEquals(user.getUsername(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
        assertEquals(user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toList()),
                userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
    }
}
