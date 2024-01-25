
package com.tcscontrol.control_backend.controllers;

import com.tcscontrol.control_backend.pessoa.user.UserService;
import com.tcscontrol.control_backend.pessoa.user.impl.mapper.UserMapper;
import com.tcscontrol.control_backend.pessoa.user.model.entity.User;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import com.tcscontrol.control_backend.auth.dto.AccessTokenResponse;
import com.tcscontrol.control_backend.auth.dto.AuthRequest;
import com.tcscontrol.control_backend.auth.dto.JwtResponse;
import com.tcscontrol.control_backend.auth.dto.RefreshTokenRequest;
import com.tcscontrol.control_backend.auth.model.RefreshToken;
import com.tcscontrol.control_backend.auth.services.RefreshTokenService;
import com.tcscontrol.control_backend.auth.services.TokenService;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public JwtResponse authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        User user = userService.login(authRequest.login());
        if (user != null) {
            var usernamePassword = new UsernamePasswordAuthenticationToken(user.getNrMatricula(),
                    authRequest.password());
            var auth = authenticationManager.authenticate(usernamePassword);
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getNrMatricula());
            var token = tokenService.generateToken((User) auth.getPrincipal());
            return new JwtResponse(token, refreshToken.getToken(), userMapper.toCreateDto(user));
        }else{
            throw new UsernameNotFoundException("Usuário não encontrado !");
        }

    }

    @PostMapping("/refreshToken")
    public AccessTokenResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return refreshTokenService.findByToken(refreshTokenRequest.getRefreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = tokenService.generateToken(user);
                    return AccessTokenResponse.builder()
                            .accessToken(accessToken)
                            .build();
                }).orElseThrow(() -> new RuntimeException(
                        "Refresh token is not in database!"));
    }

}