package com.rumor.slowmunchserver.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserCreateEvent {
    private final String subject;
    private final String email;
}
