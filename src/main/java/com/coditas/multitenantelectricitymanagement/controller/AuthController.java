package com.coditas.multitenantelectricitymanagement.controller;

import com.coditas.multitenantelectricitymanagement.constants.ApiPath;
import com.coditas.multitenantelectricitymanagement.dto.ApplicationResponse;
import com.coditas.multitenantelectricitymanagement.dto.login.LoginRequest;
import com.coditas.multitenantelectricitymanagement.dto.login.LoginResponse;
import com.coditas.multitenantelectricitymanagement.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPath.BASE_PATH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(ApiPath.LOGIN)
    public ResponseEntity<ApplicationResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request){
        LoginResponse response = authService.login(request);

        return ResponseEntity.ok().body(
                ApplicationResponse.<LoginResponse>builder()
                        .success(true)
                        .message("Successfully logged In")
                        .data(response)
                        .build()
        );

    }
}
