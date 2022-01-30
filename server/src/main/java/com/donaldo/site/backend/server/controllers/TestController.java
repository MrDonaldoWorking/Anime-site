package com.donaldo.site.backend.server.controllers;

import com.donaldo.site.backend.server.models.Streams;
import com.donaldo.site.backend.server.models.Titles;
import com.donaldo.site.backend.server.models.projections.IdAndTitle;
import com.donaldo.site.backend.server.payload.request.AccessRequest;
import com.donaldo.site.backend.server.service.TestService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/spring/test")
public class TestController {
    final TestService service;

    public TestController(final TestService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public String allAccess() {
        return service.all();
    }

    @GetMapping("/user")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String userAccess(@Valid @RequestBody AccessRequest accessRequest) {
        return service.user(accessRequest);
    }

    @GetMapping("/admin")
//    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess(@Valid @RequestBody AccessRequest accessRequest) {
        return service.admin(accessRequest);
    }

    @GetMapping("/titles")
    public List<IdAndTitle> getTitles() {
        return service.getTitles();
    }

    @GetMapping("/series/{id}")
    public Titles getFollowingSeries(@PathVariable int id) {
        return service.getFollowingSeries(id);
    }

    @GetMapping("/streams")
    public List<Streams> getStreams() {
        return service.getStreams();
    }
}
