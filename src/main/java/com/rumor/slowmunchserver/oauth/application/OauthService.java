package com.rumor.slowmunchserver.oauth.application;

import com.rumor.slowmunchserver.oauth.domain.Authentication;
import com.rumor.slowmunchserver.oauth.domain.Social;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OauthService {

    private final Authentication authentication;

    public String authenticate(Social social, String code) throws IOException {
        return authentication.authenticate(social, code);
    }
}
