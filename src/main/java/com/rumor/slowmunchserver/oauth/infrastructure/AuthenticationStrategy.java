package com.rumor.slowmunchserver.oauth.infrastructure;

import com.rumor.slowmunchserver.user.domain.User;
import com.rumor.slowmunchserver.utils.properties.oauth.OauthProperties;

import java.io.IOException;

public interface AuthenticationStrategy {

    User authenticate(OauthProperties oauthProperties, String code) throws IOException;

}
