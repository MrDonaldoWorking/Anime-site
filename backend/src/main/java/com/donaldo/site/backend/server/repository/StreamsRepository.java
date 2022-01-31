package com.donaldo.site.backend.server.repository;

import com.donaldo.site.backend.server.models.Streams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StreamsRepository extends JpaRepository<Streams, Long> {
    List<Streams> findAll();
}
