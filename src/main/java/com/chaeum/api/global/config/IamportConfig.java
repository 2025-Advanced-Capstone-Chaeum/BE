package com.chaeum.api.global.config;

import com.chaeum.api.global.properties.PortOneProperties;
import com.siot.IamportRestClient.IamportClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class IamportConfig {

    private final PortOneProperties portOneProperties;

    @Bean
    public IamportClient iamportClient() {
        return new IamportClient(
                portOneProperties.getKey(),
                portOneProperties.getSecretkey()
        );
    }
}
