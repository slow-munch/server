package com.rumor.slowmunchserver.user.infarastructure;

import com.rumor.slowmunchserver.user.application.UserService;
import com.rumor.slowmunchserver.user.domain.User;
import com.rumor.slowmunchserver.user.domain.UserCreateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class UserCreateEventHandler {
    private final UserService userService;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handle(UserCreateEvent event) {
        User user = new User(event.getSubject(), event.getEmail());
        userService.createUser(user);
    }
}
