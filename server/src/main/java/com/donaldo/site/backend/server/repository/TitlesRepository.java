package com.donaldo.site.backend.server.repository;

import com.donaldo.site.backend.server.models.Titles;
import com.donaldo.site.backend.server.models.projections.IdAndTitle;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TitlesRepository extends JpaRepository<Titles, Long> {
    @Query("select new com.donaldo.site.backend.server.models.projections.IdAndTitle(p.id, p.title) from Titles p")
    List<IdAndTitle> findAllTitles();

    List<Titles> findById(final int id);
}
