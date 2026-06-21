package com.coditas.multitenantelectricitymanagement.service;

import com.coditas.multitenantelectricitymanagement.constants.ExceptionConstants;
import com.coditas.multitenantelectricitymanagement.dto.login.LoginRequest;
import com.coditas.multitenantelectricitymanagement.dto.login.LoginResponse;
import com.coditas.multitenantelectricitymanagement.entity.RefreshToken;
import com.coditas.multitenantelectricitymanagement.entity.User;
import com.coditas.multitenantelectricitymanagement.exception.InvalidCredentialsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final RefreshTokenService refreshTokenService;

    public LoginResponse login(LoginRequest request) {

        Authentication authentication = null;

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (Exception e) {
            throw new InvalidCredentialsException(ExceptionConstants.INVALID_CREDENTIAL);
        }


        if (authentication.isAuthenticated()) {
            UserDetails user = (UserDetails) authentication.getPrincipal();

            assert user != null;
            String accessToken = jwtService.generateAccessToken(user);
            RefreshToken refreshToken = refreshTokenService.generateRefreshToken(user);
            return LoginResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken.getToken())
                    .build();
        } else {
            throw new InvalidCredentialsException(ExceptionConstants.INVALID_CREDENTIAL);
        }

    }

    public LoginResponse refreshToken(String refreshToken) {
        RefreshToken verifiedRefreshToken = refreshTokenService.verifyRefreshToken(refreshToken);

        User user = verifiedRefreshToken.getUser();
        String accessToken = jwtService.generateAccessToken(user);
        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(verifiedRefreshToken.getToken())
                .build();
    }
}
