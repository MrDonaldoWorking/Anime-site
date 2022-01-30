package com.donaldo.site.backend.server.config;

import com.donaldo.site.backend.server.models.User;

import javax.servlet.http.HttpSession;

public class Session {
    public static final String userNameSessionKey = "userName";
    public static final Session INSTANCE;

    private Session() {
    }

    static {
        INSTANCE = new Session();
    }

    public void setUser(final HttpSession httpSession, final User user) {
        httpSession.setAttribute(userNameSessionKey, user.getUsername());
    }

    public void removeUser(final HttpSession httpSession) {
        httpSession.removeAttribute(userNameSessionKey);
    }

    public String getUserName(final HttpSession httpSession) {
        return (String) httpSession.getAttribute(userNameSessionKey);
    }
}
