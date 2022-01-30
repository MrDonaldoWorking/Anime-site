package com.donaldo.site.backend.server.models;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "title_id")
    private Titles title;

    @Column(name = "comment", length = 4096)
    private String comment;

    public Comments() {

    }

    public Comments(final Titles title, final User author, final String comment) {
        this.author = author;
        this.title = title;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(final User author) {
        this.author = author;
    }

    public Titles getTitle() {
        return title;
    }

    public void setTitle(final Titles title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }
}
