package com.rumor.slowmunchserver.resolver;

import com.rumor.slowmunchserver.user.domain.UserSession;
import com.rumor.slowmunchserver.utils.Jwt;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class UserSessionArgumentResolver implements HandlerMethodArgumentResolver {

    private final Jwt jwt;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String authorization = webRequest.getHeader(HttpHeaders.AUTHORIZATION);
        String token = authorization.replaceAll("Bearer ", "");

        if (!jwt.validateToken(token)) {
            throw new RuntimeException("토큰 인증 실패");
        }

        DefaultClaims claims = (DefaultClaims) jwt.getClaims(token);
        return new UserSession(claims.getSubject(), claims.get("email", String.class));
    }
}
