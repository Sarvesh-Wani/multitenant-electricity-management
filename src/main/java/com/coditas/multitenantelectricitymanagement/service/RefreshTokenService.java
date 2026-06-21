package com.coditas.multitenantelectricitymanagement.service;

import com.coditas.multitenantelectricitymanagement.constants.ExceptionConstants;
import com.coditas.multitenantelectricitymanagement.entity.RefreshToken;
import com.coditas.multitenantelectricitymanagement.entity.User;
import com.coditas.multitenantelectricitymanagement.exception.RefreshTokenExpiredException;
import com.coditas.multitenantelectricitymanagement.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken generateRefreshToken(UserDetails user) {
        RefreshToken refreshToken = RefreshToken.builder()
                .user((User)user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plus(Duration.ofDays(7)))
                .build();
        return refreshTokenRepository.save(refreshToken);

    }

    public RefreshToken verifyRefreshToken(String refreshToken) {
        RefreshToken refreshTokenDB = refreshTokenRepository.findByToken(refreshToken);

        if (refreshTokenDB.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshTokenDB);
            throw new RefreshTokenExpiredException(ExceptionConstants.REFRESHTOKEN_EXPIRED);
        }
        return refreshTokenDB;
    }


}
