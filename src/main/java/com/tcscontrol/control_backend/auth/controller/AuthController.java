package com.tcscontrol.control_backend.auth.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import com.tcscontrol.control_backend.auth.dto.AccessTokenResponse;
import com.tcscontrol.control_backend.auth.dto.AuthRequest;
import com.tcscontrol.control_backend.auth.dto.JwtResponse;
import com.tcscontrol.control_backend.auth.dto.RefreshTokenRequest;
import com.tcscontrol.control_backend.auth.model.RefreshToken;
import com.tcscontrol.control_backend.auth.services.JwtService;
import com.tcscontrol.control_backend.auth.services.RefreshTokenService;
import com.tcscontrol.control_backend.user.UserService;
import com.tcscontrol.control_backend.user.model.entity.User;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    private final UserService userService;


    @PostMapping("/login")
    public JwtResponse authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(authRequest.login(), authRequest.password());
        if (!usernamePassword.isAuthenticated()) {
            User user = userService.login(authRequest.login()).get();
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getNrMatricula().toString());
            return new JwtResponse(jwtService.generateToken(user.getNrMatricula().toString()), refreshToken.getToken(), user);
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }
    
    @PostMapping("/refreshToken")
    public AccessTokenResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return refreshTokenService.findByToken(refreshTokenRequest.getRefreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtService.generateToken(user.getNrMatricula().toString());
                    return AccessTokenResponse.builder()
                            .accessToken(accessToken)
                            .build();
                }).orElseThrow(() -> new RuntimeException(
                        "Refresh token is not in database!"));
    }

}
