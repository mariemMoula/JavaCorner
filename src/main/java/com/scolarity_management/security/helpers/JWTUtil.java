package com.scolarity_management.security.helpers;

public class JWTUtil {
    //4
    // This is a utility class that will be used to store the secret key used to sign the JWT tokens
    public static final Integer EXPIRE_ACCESS_TOKEN = 8;
    public static final Integer EXPIRE_REFRESH_TOKEN = 10;
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String ISSUER = "ScolarityManagement";
    public static final String SECRET = "myPrivateSecret";
    public static final String AUTH_HEADER = "Authorization";
}

