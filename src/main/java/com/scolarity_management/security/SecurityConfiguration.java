package com.scolarity_management.security;

import com.scolarity_management.exceptions.AccessDeniedHandler;
import com.scolarity_management.security.filters.JWTAuthorizationFilter;
import com.scolarity_management.security.helpers.JWTHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
//1
    @Autowired
    private UserDetailsService userDetailsService;
    private final JWTHelper jwtHelper;
    // This is the security configuration class that will be used to configure the security settings of our application, it is annotated with @EnableWebSecurity to enable the web security features provided by Spring Security
    public SecurityConfiguration(JWTHelper jwtHelper) {
        this.jwtHelper = jwtHelper;
    }
// This method is used to configure the security settings of our application, it takes an HttpSecurity object as a parameter which is used to configure the security settings , here s how it works : we first configure the CORS settings to allow requests from specific origins and methods , then we disable CSRF protection as we are using JWT tokens for authentication and we don't need CSRF protection , then we configure the session management to be stateless as we are using JWT tokens for authentication and we don't need sessions , then we configure the authorization settings to permit requests to the swagger UI and API documentation endpoints and to require authentication for all other requests , then we add a JWT authorization filter before the UsernamePasswordAuthenticationFilter to authenticate the user using the JWT token , and finally we configure the exception handling to return a 403 status code when the user is not authenticated
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // Configure CORS settings to allow requests from specific origins and methods , here we are allowing requests from localhost and localhost:3000 and we are allowing GET, POST, PUT, DELETE and OPTIONS methods , we are also allowing all headers and we are exposing the Content-Disposition header
            .cors(cors -> cors.configurationSource(httpServletRequest -> {
                var corsConfiguration = new org.springframework.web.cors.CorsConfiguration();
                corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost", "https://localhost", "http://localhost:3000", "https://localhost:3000"));
                corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                // This is used to allow all headers which means that the client can send any header in the request
                corsConfiguration.setAllowedHeaders(List.of("*"));
                // This is used to expose the Content-Disposition header to the client , which is used to download files from the server , this is required when the client is downloading files from the server for example when the client is downloading a PDF file
                corsConfiguration.setExposedHeaders(List.of("Content-Disposition"));
                return corsConfiguration;
            }))
             // Disable CSRF protection as we are using JWT tokens for authentication and we don't need CSRF protection since JWT tokens are not vulnerable to CSRF attacks
            .csrf(AbstractHttpConfigurer::disable)
             // Configure the session management to be stateless as we are using JWT tokens for authentication and we don't need sessions
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
             // Configure the authorization settings to permit requests to the swagger UI and API documentation endpoints and to require authentication for all other requests
            .authorizeHttpRequests(authz -> authz
                    .requestMatchers("/auth/**").permitAll() // permit all requests to the /auth/** endpoint which is used to authenticate the user
                    .requestMatchers("/api/v1/users/refresh-token").permitAll() // permit all requests to the /api/v1/users/refresh-token endpoint which is used to refresh the access token
                    .requestMatchers(  // permit all requests to the swagger UI and API documentation endpoints
                            "/v2/**",
                            "/v3/**",
                            "/api-docs/**",
                            "/swagger-resources/**",
                            "/swagger-ui/**",
                            "/swagger-ui.html").permitAll()
                    .anyRequest().authenticated() // require authentication for all other requests
            )
                .addFilterBefore(new JWTAuthorizationFilter(jwtHelper), UsernamePasswordAuthenticationFilter.class)   // Add a JWT authorization filter before the UsernamePasswordAuthenticationFilter to authenticate the user using the JWT token
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(new AccessDeniedHandler())) // Configure the exception handling to return a 403 status code when the user is not authenticated
        .build();
    }
// This method is used to create an authentication manager bean that is used to authenticate the user , it takes an AuthenticationConfiguration object as a parameter which is used to create the authentication manager , the authentication manager is used to authenticate the user using the authentication provider
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}

/**
 * CORS (Cross-Origin Resource Sharing) is a security mechanism enforced by web browsers to control how resources on a web page (like APIs) can be accessed from another domain.
 * It ensures that web applications running on different origins (domains, protocols, or ports) can only interact in controlled ways,
 * preventing potential security risks like cross-site scripting (XSS).
 */