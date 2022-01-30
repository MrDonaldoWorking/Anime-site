package com.donaldo.site.backend.server.controllers;

import com.donaldo.site.backend.server.models.Comments;
import com.donaldo.site.backend.server.models.Titles;
import com.donaldo.site.backend.server.models.User;
import com.donaldo.site.backend.server.payload.request.CommentRequest;
import com.donaldo.site.backend.server.repository.CommentsRepository;
import com.donaldo.site.backend.server.repository.TitlesRepository;
import com.donaldo.site.backend.server.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/spring/comments")
public class CommentController {
    final CommentsRepository commentsRepository;
    final UserRepository userRepository;
    final TitlesRepository titlesRepository;

    public CommentController(final CommentsRepository commentsRepository,
                             final UserRepository userRepository,
                             final TitlesRepository titlesRepository) {
        this.commentsRepository = commentsRepository;
        this.userRepository = userRepository;
        this.titlesRepository = titlesRepository;
    }

    @GetMapping("/all/{id}")
    public List<Comments> allCommentsByTitle(@PathVariable int id) {
        return commentsRepository.findAllByTitleId(id);
    }

    private TAndU isValidTU(final int titleId, final long userId) {
        final Optional<User> user = userRepository.findById(userId);
        final Optional<Titles> title = titlesRepository.findById(titleId);
        if (user.isEmpty() || title.isEmpty()) {
            return new TAndU(null, null);
        }
        return new TAndU(title.get(), user.get());
    }

    private Comments isValidC(final Titles title, final User user, final int commentId) {
        final Optional<Comments> comment = commentsRepository.findById(commentId);

        if (comment.isPresent()) {
            final Comments commentVal = comment.get();
            if (commentVal.getTitle().getId() != title.getId()
                    || commentVal.getAuthor().getId() != user.getId()) {
                return null;
            }
            return commentVal;
        }
        return null;
    }

    @PostMapping("/new/{titleId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Comments> postCommentByTitle(@PathVariable int titleId,
                                                       @Valid @RequestBody CommentRequest commentRequest) {
        final TAndU tAndU = isValidTU(titleId, commentRequest.getUserId());
        if (tAndU.isNull()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        try {
            commentsRepository.save(new Comments(tAndU.getTitle(), tAndU.getUser(), commentRequest.getComment()));
        } catch (final IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (final RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{titleId}/{commentId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Comments> editComment(@PathVariable int titleId, @PathVariable int commentId,
                                                @Valid @RequestBody CommentRequest commentRequest) {
        final TAndU tAndU = isValidTU(titleId, commentRequest.getUserId());
        if (tAndU.isNull()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        final Comments comment = isValidC(tAndU.getTitle(), tAndU.getUser(), commentId);
        if (comment != null) {
            comment.setComment(commentRequest.getComment());
            commentsRepository.save(comment);
            return ResponseEntity.ok(comment);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{titleId}/{commentId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Comments> deleteComment(@PathVariable int titleId, @PathVariable int commentId,
                                                  @Valid @RequestBody CommentRequest commentRequest) {
        final TAndU tAndU = isValidTU(titleId, commentRequest.getUserId());
        if (tAndU.isNull()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        final Comments comment = isValidC(tAndU.getTitle(), tAndU.getUser(), commentId);
        if (comment != null) {
            commentsRepository.deleteById((long) commentId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

class TAndU {
    private final Titles title;
    private final User user;

    TAndU(final Titles title, final User user) {
        this.title = title;
        this.user = user;
    }

    public Titles getTitle() {
        return title;
    }

    public User getUser() {
        return user;
    }

    public boolean isNull() {
        return title == null || user == null;
    }
}