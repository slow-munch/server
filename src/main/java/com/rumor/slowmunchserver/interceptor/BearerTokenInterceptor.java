package com.rumor.slowmunchserver.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rumor.slowmunchserver.user.domain.User;
import com.rumor.slowmunchserver.user.domain.UserSession;
import com.rumor.slowmunchserver.utils.Jwt;
import io.jsonwebtoken.impl.DefaultClaims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
@RequiredArgsConstructor
public class BearerTokenInterceptor implements HandlerInterceptor {

    private final Jwt jwt;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        // JWT 여부 확인
        String token = authorization.replaceAll("Bearer ", "");

        // token의 값이 정상적인지 확인
        if (token != null && token.length() > 10) {
            log.debug("토큰 상태:: " + token);

            // token 유효성 검증
            if (jwt.validateToken(token)) {
                return true;
            }
        } else {
            throw new RuntimeException("ErrorCode.VALID_MEMBER");
        }
        return false;
    }
}
