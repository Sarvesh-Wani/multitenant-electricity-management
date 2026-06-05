package com.coditas.multitenantelectricitymanagement.service;

import com.coditas.multitenantelectricitymanagement.constants.ExceptionConstants;
import com.coditas.multitenantelectricitymanagement.dto.login.LoginRequest;
import com.coditas.multitenantelectricitymanagement.dto.login.LoginResponse;
import com.coditas.multitenantelectricitymanagement.entity.User;
import com.coditas.multitenantelectricitymanagement.exception.InvalidCredentialsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
                );

        if(authentication.isAuthenticated()){
            User user = (User) authentication.getPrincipal();

            String accessToken = jwtService.generateAccessToken(user);

            return LoginResponse.builder()
                    .acessToken(accessToken)
                    .build();
        }else{
            throw new InvalidCredentialsException(ExceptionConstants.INVALIDCREDENTIAL);
        }

    }
}
