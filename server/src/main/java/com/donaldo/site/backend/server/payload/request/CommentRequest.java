package com.donaldo.site.backend.server.payload.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class CommentRequest {
    @NotBlank
    private String comment;

    @Min(0)
    private long userId;

    public String getComment() {
        return comment;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(final long userId) {
        this.userId = userId;
    }
}
