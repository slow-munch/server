package com.rumor.slowmunchserver.oauth.domain;

import java.io.IOException;

public interface Authentication {

    String authenticate(Social social, String code) throws IOException;
}
