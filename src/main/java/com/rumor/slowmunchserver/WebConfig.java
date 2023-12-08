package com.rumor.slowmunchserver;

import com.rumor.slowmunchserver.interceptor.BearerTokenInterceptor;
import com.rumor.slowmunchserver.resolver.UserSessionArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final BearerTokenInterceptor bearerTokenInterceptor;
    private final UserSessionArgumentResolver userSessionArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(bearerTokenInterceptor)
                .order(1)   // interceptor에서 실행 순서
                .addPathPatterns("/**") // interceptor가 실행될 url 패턴 (배열 형식으로 입력 가능)
        .excludePathPatterns("/oauth2/**")    // interceptor가 실행되지 않을 url 패턴 (배열 형식으로 입력 가능)
        ;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userSessionArgumentResolver);
    }
}