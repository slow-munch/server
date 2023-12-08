package com.rumor.slowmunchserver.utils.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "resources.directory")
@RequiredArgsConstructor
public class ResourcesProperties {
    private final String path;
}
