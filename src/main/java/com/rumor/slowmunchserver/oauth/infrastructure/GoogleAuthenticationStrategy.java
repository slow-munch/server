package com.rumor.slowmunchserver.oauth.infrastructure;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.rumor.slowmunchserver.oauth.domain.Social;
import com.rumor.slowmunchserver.user.domain.User;
import com.rumor.slowmunchserver.user.domain.UserCreateEvent;
import com.rumor.slowmunchserver.utils.properties.oauth.OauthProperties;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;

@Slf4j
@Component
@RequiredArgsConstructor
public class GoogleAuthenticationStrategy implements AuthenticationStrategy {

    private final RestTemplate restTemplate;
    private final ApplicationEventPublisher publisher;

    @Override
    public User authenticate(OauthProperties oauthProperties, String code) throws IOException {
        HttpEntity googleOauthRequest = makeGoogleRequestEntity(oauthProperties, code);
        SocialOauthResponse accessToken = requestAccessToken(oauthProperties.getGoogle().getApiUrl(), googleOauthRequest);

        return verifyToken(accessToken, oauthProperties.getClientId(Social.GOOGLE));
    }

    private SocialOauthResponse requestAccessToken(String googleOauthUrl, HttpEntity googleOauthRequest) {
        ResponseEntity<SocialOauthResponse> response = restTemplate.postForEntity(googleOauthUrl, googleOauthRequest, SocialOauthResponse.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }

        throw new IllegalStateException("서버 요청 실패 " + response.getBody());
    }

    private HttpEntity makeGoogleRequestEntity(OauthProperties oauthProperties, String code) {
        return new HttpEntity<>(SocialOauthRequest.of(
                code,
                oauthProperties.getClientId(Social.GOOGLE),
                oauthProperties.getClientSecret(Social.GOOGLE),
                oauthProperties.getGoogle().getRedirectUri()
        ), makeHeader());
    }


    private HttpHeaders makeHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }
    private User verifyToken(SocialOauthResponse response, String clientId) throws IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singleton(clientId))
                .build();


        GoogleIdToken idToken = GoogleIdToken.parse(verifier.getJsonFactory(), response.getIdToken());

        if (idToken == null) {
            throw new RuntimeException("해당 토큰으로 유저를 찾을 수 없음");
        }

        GoogleIdToken.Payload payload = idToken.getPayload();

        String subject = payload.getSubject();
        String email = payload.getEmail();
        String locale = (String) payload.get("locale");

        StringUtils.isNotBlank(subject);
        publisher.publishEvent(new UserCreateEvent(subject, email));

        return new User(subject, email);
    }
}
