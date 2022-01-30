package com.donaldo.site.backend.server.controllers;

import com.donaldo.site.backend.server.models.Streams;
import com.donaldo.site.backend.server.models.Titles;
import com.donaldo.site.backend.server.models.projections.IdAndTitle;
import com.donaldo.site.backend.server.repository.StreamsRepository;
import com.donaldo.site.backend.server.repository.TitlesRepository;
import com.donaldo.site.backend.server.service.TestService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/spring/test")
public class TestController {
    private final TestService testService;

    public TestController(final TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/all")
    public String allAccess() {
        return testService.all();
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String userAccess() {
        return testService.user();
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return testService.admin();
    }

    @GetMapping("/titles")
    public List<IdAndTitle> getTitles() {
        return testService.getTitles();
    }

    @GetMapping("/series/{id}")
    public Titles getFollowingSeries(@PathVariable int id) {
        return testService.getFollowingSeries(id);
    }

    @GetMapping("/streams")
    public List<Streams> getStreams() {
        return testService.getStreams();
    }
}
