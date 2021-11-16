package com.donaldo.site.backend.server.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "titles")
public class Titles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String title;

    @NotBlank
    private String imageUrl;

    @NotBlank
    @Column(name = "description", length = 8192)
    private String description;

    public Titles() {

    }

    public Titles(final String title, final String imageUrl, final String description) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public Titles(final int id, final String title, final String imageUrl, final String description) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }
}
