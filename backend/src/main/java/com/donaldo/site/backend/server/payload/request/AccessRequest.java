package com.donaldo.site.backend.server.payload.request;

import javax.validation.constraints.NotBlank;

public class AccessRequest {
    @NotBlank
    private String username;

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
