package com.tcscontrol.control_backend.auth.services;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcscontrol.control_backend.auth.model.RefreshToken;
import com.tcscontrol.control_backend.auth.repository.RefreshTokenRepository;
import com.tcscontrol.control_backend.user.UserNegocio;
import com.tcscontrol.control_backend.user.model.entity.User;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    private UserNegocio userNegocio;

    public RefreshToken createRefreshToken(String login) {
        User user = userNegocio.login(login).get();
        RefreshToken rt = refreshTokenRepository.findByUser(user);
        RefreshToken refreshToken = RefreshToken.builder()
                .id(rt != null ? rt.getId() : null)
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000))// 10
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException(
                    token.getToken() + " Refresh token was expired. Please make a new signin request");
        }
        return token;
    }
}
