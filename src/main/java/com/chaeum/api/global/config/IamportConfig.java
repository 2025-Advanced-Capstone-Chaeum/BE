package com.chaeum.api.global.config;

import com.chaeum.api.global.properties.IamportProperties;
import com.siot.IamportRestClient.IamportClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class IamportConfig {

    private final IamportProperties iamportProperties;

    @Bean
    public IamportClient iamportClient() {
        return new IamportClient(
                iamportProperties.getKey(),
                iamportProperties.getSecretkey()
        );
    }
}
