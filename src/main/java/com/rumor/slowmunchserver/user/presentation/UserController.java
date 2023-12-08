package com.rumor.slowmunchserver.user.presentation;

import com.rumor.slowmunchserver.oauth.application.OauthService;
import com.rumor.slowmunchserver.user.application.UserService;
import com.rumor.slowmunchserver.user.domain.User;
import com.rumor.slowmunchserver.user.domain.UserSession;
import com.rumor.slowmunchserver.utils.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public UserSession me(UserSession userSession) {
        return userSession;
    }
}
