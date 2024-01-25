package com.tcscontrol.control_backend.auth.dto;

import com.tcscontrol.control_backend.pessoa.user.model.dto.UserCreateDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {

    private String accessToken;
    private String refreshToken;
    private UserCreateDTO user;
    
}
