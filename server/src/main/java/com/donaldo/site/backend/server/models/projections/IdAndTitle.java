package com.donaldo.site.backend.server.models.projections;

public class IdAndTitle {
    private final int id;
    private final String title;

    public IdAndTitle(final int id, final String title) {
        this.id = id;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }
}
