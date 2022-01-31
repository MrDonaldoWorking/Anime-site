package com.donaldo.site.backend.server.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "streams")
public class Streams {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String href;

    @NotBlank
    private String name;

    public Streams() {

    }

    public Streams(final String href, final String name) {
        this.href = href;
        this.name = name;
    }

    public Streams(final int id, final String href, final String name) {
        this.id = id;
        this.href = href;
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(final String href) {
        this.href = href;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
