package com.scolarity_management.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.scolarity_management.security.helpers.JWTHelper;
import com.scolarity_management.security.helpers.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

    //5
    private final JWTHelper jwtHelper;
    public JWTAuthorizationFilter(JWTHelper jwtHelper) {
        this.jwtHelper = jwtHelper;
    }

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws ServletException, IOException {
        // Extract the token from the header using the helper class
        String accessToken = jwtHelper.extractTokenFromHeaderIfExists(request.getHeader(JWTUtil.AUTH_HEADER));
        // If the token is not null and is valid then continue the filter chain
        if (accessToken != null) {
           // Verify the token using the secret key , here is how it works : the token is signed using the secret key and the algorithm HMAC256 , so when we verify the token we use the same secret key and algorithm to verify the token , if the token is valid then we can extract the email and roles from it and create an authentication object and set it in the security context holder so that the user is authenticated and authorized to access the resources
            Algorithm algorithm = Algorithm.HMAC256(JWTUtil.SECRET);
            // Create a JWT verifier object using the algorithm , it is used to verify the token and extract the claims from it
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            // Verify the token and extract the claims from it using the verifier object
            DecodedJWT decodedJWT = jwtVerifier.verify(accessToken);
            // Extract the email and roles from the claims and create an authentication object and set it in the security context holder
            String email = decodedJWT.getSubject();
            /*By converting roles to GrantedAuthority, you ensure that the roles can be seamlessly integrated into Spring Security's authentication and authorization mechanisms.
            */
            Collection<GrantedAuthority> authorities = new ArrayList<>(); // an array for the roles
            decodedJWT.getClaim("roles").asList(String.class).forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_"+role)));

            // Create an authentication object and set it in the security context holder to authenticate the user
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,
                    null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        // Continue the filter chain
        filterChain.doFilter(request, response);


    }

}
