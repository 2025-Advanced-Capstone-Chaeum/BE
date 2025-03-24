package com.chaeum.api.global.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "spring.portone")
public class IamportProperties {

    private String code;
    private String key;
    private String secretkey;
}
