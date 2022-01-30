package com.donaldo.site.backend.server.service;

import com.donaldo.site.backend.server.models.Streams;
import com.donaldo.site.backend.server.models.Titles;
import com.donaldo.site.backend.server.models.projections.IdAndTitle;
import com.donaldo.site.backend.server.repository.StreamsRepository;
import com.donaldo.site.backend.server.repository.TitlesRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class TestService {
    final TitlesRepository titlesRepository;
    final StreamsRepository streamsRepository;

    public TestService(final TitlesRepository titlesRepository, final StreamsRepository streamsRepository) {
        this.titlesRepository = titlesRepository;
        this.streamsRepository = streamsRepository;
    }

    public String all() {
        return "Public Content.";
    }

    public String user() {
        return "User Content.";
    }

    public String admin() {
        return "Admin Board.";
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
