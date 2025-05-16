package com.chaeum.api.global.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class TokenConstants {

    public static final String ACCESS_TOKEN_COOKIE_NAME = "AccessToken";
    public static final String ACCESS_TOKEN_CATEGORY = "access";

    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh";
    public static final String REFRESH_TOKEN_CATEGORY = "refresh";

    public static final String AUTH_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    public static final String LOGOUT_ENDPOINT = "/api/v1/logout";
    public static final String POST_METHOD = "POST";
}
