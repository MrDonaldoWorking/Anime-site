package com.donaldo.site.backend.server.service;

import com.donaldo.site.backend.server.models.*;
import com.donaldo.site.backend.server.models.projections.IdAndTitle;
import com.donaldo.site.backend.server.payload.request.AccessRequest;
import com.donaldo.site.backend.server.repository.RoleRepository;
import com.donaldo.site.backend.server.repository.StreamsRepository;
import com.donaldo.site.backend.server.repository.TitlesRepository;
import com.donaldo.site.backend.server.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TestService {
    final TitlesRepository titlesRepository;
    final StreamsRepository streamsRepository;
    final UserRepository userRepository;
    final RoleRepository roleRepository;

    public TestService(final TitlesRepository titlesRepository, final StreamsRepository streamsRepository,
                       final UserRepository userRepository, final RoleRepository roleRepository) {
        this.titlesRepository = titlesRepository;
        this.streamsRepository = streamsRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public String all() {
        return "Public Content.";
    }

    private User getCurrentUser(final AccessRequest accessRequest) {
        return userRepository.findByUsername(accessRequest.getUsername())
                .orElse(null);
    }

    public String user(final AccessRequest accessRequest) {
        final User user = getCurrentUser(accessRequest);
        final Role role = roleRepository.findByName(ERole.ROLE_USER).orElseThrow();
        if (user != null && user.getRoles().contains(role)) {
            return "User Content.";
        } else {
            return "Forbidden.";
        }
    }

    public String admin(final AccessRequest accessRequest) {
        final User user = getCurrentUser(accessRequest);
        final Role admin = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow();
        if (user != null && user.getRoles().contains(admin)) {
            return "Admin Board.";
        } else {
            return "Forbidden.";
        }
    }

    public List<IdAndTitle> getTitles() {
        return titlesRepository.findAllTitles();
    }

    public Titles getFollowingSeries(final int id) {
        final Optional<Titles> title = titlesRepository.findById(id);
        return title.orElse(null);
    }

    public List<Streams> getStreams() {
        return streamsRepository.findAll();
    }
}
