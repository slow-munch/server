package com.rumor.slowmunchserver.oauth.infrastructure;

import com.rumor.slowmunchserver.oauth.domain.Authentication;
import com.rumor.slowmunchserver.oauth.domain.Social;
import com.rumor.slowmunchserver.user.domain.User;
import com.rumor.slowmunchserver.utils.Jwt;
import com.rumor.slowmunchserver.utils.properties.oauth.OauthProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationImpl implements Authentication {

    private final Jwt jwt;
    private final OauthProperties oauthProperties;
    private final AuthenticationFactory authenticationFactory;

    @Override
    public String authenticate(Social social, String code) throws IOException {
        AuthenticationStrategy authentication = authenticationFactory.create(social);
        User user = authentication.authenticate(oauthProperties, code);
        return jwt.createToken(user);
    }
}
