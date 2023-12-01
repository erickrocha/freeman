package com.erocha.freeman.commons.security.filter;

public class SecurityConstants {

    private SecurityConstants() {
    }

    public static final String SECRET = "t6w9z$C&F)J@NcRfUjXn2r5u8x!A%D*G-KaPdSgVkYp3s6v9y$B?E(H+MbQeThWm";
    public static final long EXPIRATION_TIME = 259200000; // 3 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/login";
    public static final String ROLES = "roles";
    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_ISSUER = "order-api";
    public static final String TOKEN_AUDIENCE = "order-app";
}
