package com.scolarity_management.service;

import com.scolarity_management.dto.LoginRequestDTO;
import com.scolarity_management.dto.LoginResponseDTO;


public interface AuthService {
    LoginResponseDTO authenticate(LoginRequestDTO loginRequestDTO);
}


