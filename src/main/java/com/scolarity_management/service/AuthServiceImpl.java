package com.scolarity_management.service;

import com.scolarity_management.dto.LoginRequestDTO;
import com.scolarity_management.dto.LoginResponseDTO;
import com.scolarity_management.security.helpers.JWTHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JWTHelper jwtHelper;
    private final UserDetailsService userDetailsService;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JWTHelper jwtHelper, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtHelper = jwtHelper;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public LoginResponseDTO authenticate(LoginRequestDTO loginRequestDTO) {
        final String email = loginRequestDTO.email();
        final String password = loginRequestDTO.password();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        var user = userDetailsService.loadUserByUsername(email);
        String jwtAccessToken = jwtHelper.generateAccessToken(user.getUsername(),
                user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
        String jwtRefreshToken = jwtHelper.generateRefreshToken(user.getUsername());

        return new LoginResponseDTO(jwtAccessToken, jwtRefreshToken);
    }
}
