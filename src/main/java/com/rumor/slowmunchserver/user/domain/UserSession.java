package com.rumor.slowmunchserver.user.domain;

import lombok.Data;

@Data
public class UserSession {
    private String subject;
    private String email;

    public UserSession(String subject, String email) {
        this.subject = subject;
        this.email = email;
    }
}
