package com.rumor.slowmunchserver.oauth.presentation;

import com.rumor.slowmunchserver.oauth.application.OauthService;
import com.rumor.slowmunchserver.oauth.domain.Social;
import com.rumor.slowmunchserver.utils.properties.oauth.OauthProperties;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/oauth2")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class OauthController {
    private final OauthService oauthService;
    private final OauthProperties oauthProperties;

    @GetMapping("/{social}/login")
        public void login(@PathVariable Social social, HttpServletResponse response) throws IOException {
        response.sendRedirect(oauthProperties.getAuthenticationUrl(social));
    }

    @GetMapping("/{social}/authenticate")
    public void authenticate(@PathVariable Social social, @RequestParam String code, HttpServletResponse response) throws IOException {
        String token = oauthService.authenticate(social, code);

        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(24 * 60 * 60);

        response.addCookie(cookie);
        response.sendRedirect("http://localhost:5173");
    }
}
