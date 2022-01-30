package com.donaldo.site.backend.server.repository;

import com.donaldo.site.backend.server.models.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {
    List<Comments> findAllByTitleId(final int titleId);

    Optional<Comments> findById(final int commentId);
}
