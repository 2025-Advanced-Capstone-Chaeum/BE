package com.chaeum.api.global.config;

import com.chaeum.api.domain.member.service.MemberService;
import com.chaeum.api.global.auth.service.CustomOAuth2MemberService;
import com.chaeum.api.global.auth.service.ReissueService;
import com.chaeum.api.global.auth.util.SecurityUrlConstants;
import com.chaeum.api.global.auth.util.TokenValidator;
import com.chaeum.api.global.filter.CustomEntryPoint;
import com.chaeum.api.global.filter.CustomLogoutFilter;
import com.chaeum.api.global.filter.InternalApiKeyFilter;
import com.chaeum.api.global.filter.JwtFilter;
import com.chaeum.api.global.handler.CustomOAuth2LoginHandler;
import com.chaeum.api.global.properties.CorsProperties;
import com.chaeum.api.global.auth.util.JwtUtil;
import com.chaeum.api.global.properties.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequestEntityConverter;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final JwtProperties jwtProperties;
    private final MemberService memberService;
    private final CorsProperties corsProperties;
    private final TokenValidator tokenValidator;
    private final ReissueService reissueService;
    private final CustomEntryPoint customEntryPoint;
    private final CustomOAuth2LoginHandler customOAuth2LoginHandler;
    private final CustomOAuth2MemberService customOAuth2MemberService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            .authorizeHttpRequests(this::configureAuthorization)
            .exceptionHandling(this::configureExceptionHandling)
            .oauth2Login(this::configureOAuth2Login)

            .addFilterBefore(internalApiKeyFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(logoutFilter(), LogoutFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(corsProperties.getAllowedOrigins());
        configuration.setAllowedMethods(corsProperties.getAllowedMethods());
        configuration.setAllowedHeaders(corsProperties.getAllowedHeaders());
        configuration.setAllowCredentials(corsProperties.getAllowCredentials());
        configuration.setMaxAge(corsProperties.getMaxAge());
        configuration.setExposedHeaders(corsProperties.getAllowedHeaders());

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter(jwtUtil, memberService, tokenValidator);
    }

    @Bean
    public InternalApiKeyFilter internalApiKeyFilter() {
        return new InternalApiKeyFilter();
    }

    @Bean
    public CustomLogoutFilter logoutFilter() {
        return new CustomLogoutFilter(jwtUtil, jwtProperties, memberService, reissueService, tokenValidator);
    }

    @Bean
    public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> authorizationCodeTokenResponseClient() {
        DefaultAuthorizationCodeTokenResponseClient client = new DefaultAuthorizationCodeTokenResponseClient();
        client.setRequestEntityConverter(new OAuth2AuthorizationCodeGrantRequestEntityConverter() {
            @Override
            public RequestEntity<?> convert(OAuth2AuthorizationCodeGrantRequest request) {
                RequestEntity<?> entity = super.convert(request);
                return new RequestEntity<>(entity.getBody(), entity.getHeaders(), HttpMethod.POST, entity.getUrl());
            }
        });
        return client;
    }

    @Bean
    public OAuth2AuthorizedClientProvider authorizedClientProvider() {
        return OAuth2AuthorizedClientProviderBuilder.builder()
            .authorizationCode()
            .refreshToken()
            .clientCredentials()
            .build();
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService(
        ClientRegistrationRepository clientRegistrationRepository) {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
    }

    private void configureAuthorization(
        AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
        auth
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .requestMatchers(SecurityUrlConstants.PUBLIC_URLS).permitAll()
            .anyRequest().permitAll();
//            .anyRequest().authenticated();
//         TODO: 개발 편의성을 위해 전체 허용, 운영 시 authenticated()로 변경
    }

    private void configureExceptionHandling(ExceptionHandlingConfigurer<HttpSecurity> exceptionHandling) {
        exceptionHandling.authenticationEntryPoint(customEntryPoint);
    }

    private void configureOAuth2Login(OAuth2LoginConfigurer<HttpSecurity> oauth2) {
        oauth2
            .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2MemberService))
            .successHandler(customOAuth2LoginHandler)
            .tokenEndpoint(token -> token.accessTokenResponseClient(authorizationCodeTokenResponseClient()));
    }
}
