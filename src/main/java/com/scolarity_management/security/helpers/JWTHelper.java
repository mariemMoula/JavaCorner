package com.scolarity_management.security.helpers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JWTHelper {
    //3
    //This is a helper class that will be used to generate and validate JWT tokens

    private final Algorithm algorithm = Algorithm.HMAC256(JWTUtil.SECRET);

    // This method will generate a JWT access token for the given email and roles
    public String generateAccessToken(String email, List<String> roles) {
        return JWT.create()
                .withSubject(email) // subject is used to identify the principal that is the subject of the JWT token
                .withExpiresAt(DateUtils.addMinutes(new Date(), JWTUtil.EXPIRE_ACCESS_TOKEN))
                .withIssuer(JWTUtil.ISSUER) // issuer is used to identify the generator of the JWT token
                .withClaim("roles", roles) // claims are used to store additional information in the JWT token they are not mandatory
                .sign(algorithm); // sign the token using the secret key and the algorithm
    }


    // This method will generate a JWT refresh token for the given email
    // A refresh token is a special kind of token that is used to obtain a new access token after the access token has expired
    public String generateRefreshToken(String email) {
        return JWT.create()
                .withSubject(email)
                .withExpiresAt(DateUtils.addMinutes(new Date(), JWTUtil.EXPIRE_REFRESH_TOKEN))
                .withIssuer(JWTUtil.ISSUER)
                .sign(algorithm);
    }


    // This method will extract the token from the Authorization header if it exists and return it , it is used when the client sends the token in the Authorization header
    // Bearer is the prefix that the front end app sends before the token in the header of the http request
    // The token is preceded by the Bearer constant , and in the header of the http request , it adds barrer space then token
    public String extractTokenFromHeaderIfExists(String authorizationHeader) {
        // An aurthorization header is a header that is used to authenticate the user , it is used to send the token to the server
        //The authorizationHeader is created by the client and sent to the server in the Authorization header of the HTTP request
        if (authorizationHeader != null && authorizationHeader.startsWith(JWTUtil.BEARER_PREFIX)) {
            return authorizationHeader.substring(JWTUtil.BEARER_PREFIX.length());
        }
        return null;
    }
    //This mzthod allows us to get a map of the access and refresh tokens because when the user loggs in , we will need to send them the access and refresh token
    public Map<String,String> getTokensMap(String jwtAccessToken, String jwtRefreshToken){
        Map<String,String> idTokens = new HashMap<>() ;
        idTokens.put("accessToken",jwtAccessToken);
        idTokens.put("refreshToken",jwtRefreshToken);
        return idTokens;
    }

}
/*
The token is a string that is used to authenticate and authorize the user. It is generated by the server and sent to the client, which includes it in the Authorization header of the HTTP request.
A token is composed of three parts: a header, a payload, and a signature. The header contains the algorithm used to sign the token, the payload contains the claims, and the signature is used to verify the authenticity of the token.
The header part is a JSON object that contains the type of the token and the algorithm used to sign the token.
The payload part is also a JSON object that contains the claims.
The claims are statements about the user, such as the user's email, roles, and expiration time.
The signature part is used to verify the authenticity of the token. It is created by signing the header and payload parts
with a secret key using the algorithm specified in the header.
The token is verified by the server by extracting the header and payload parts from the token, verifying the signature using the secret key, and checking the claims.
The signature verification ensures that the token has not been tampered with, and the claims are used to authenticate and authorize the user.
We verify the signature of the token using the secret key and the algorithm HMAC256. If the token is valid, we extract the email and roles from the claims and create an authentication object with the email, roles, and authorities.
* */
