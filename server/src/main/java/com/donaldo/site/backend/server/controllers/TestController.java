package com.donaldo.site.backend.server.controllers;

import com.donaldo.site.backend.server.models.Streams;
import com.donaldo.site.backend.server.models.Titles;
import com.donaldo.site.backend.server.models.projections.IdAndTitle;
import com.donaldo.site.backend.server.repository.StreamsRepository;
import com.donaldo.site.backend.server.repository.TitlesRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/spring/test")
public class TestController {
    final TitlesRepository titlesRepository;
    final StreamsRepository streamsRepository;

    public TestController(final TitlesRepository titlesRepository, final StreamsRepository streamsRepository) {
        this.titlesRepository = titlesRepository;
        this.streamsRepository = streamsRepository;
    }

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }

    @GetMapping("/titles")
    public List<IdAndTitle> getTitles() {
        return titlesRepository.findAllTitles();
    }

    @GetMapping("/series/{id}")
    public List<Titles> getFollowingSeries(@PathVariable int id) {
        return titlesRepository.findById(id);
    }

    @GetMapping("/streams")
    public List<Streams> getStreams() {
        return streamsRepository.findAll();
    }
}
