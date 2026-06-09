package com.coditas.multitenantelectricitymanagement.exception;

import com.coditas.multitenantelectricitymanagement.dto.ApplicationResponse;
import com.coditas.multitenantelectricitymanagement.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApplicationResponse<ErrorResponse>> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest http) {

        ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMessage(ex.getMessage());
        response.setPath(http.getRequestURL().toString());
        response.setTimestamp(Instant.now());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ApplicationResponse.<ErrorResponse>builder()
                        .success(false)
                        .message("Exception Occurred")
                        .errors(response)
                        .build()
        );
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApplicationResponse<ErrorResponse>> handleDuplicateResource(DuplicateResourceException ex, HttpServletRequest http) {

        ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.CONFLICT.value());
        response.setMessage(ex.getMessage());
        response.setPath(http.getRequestURL().toString());
        response.setTimestamp(Instant.now());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ApplicationResponse.<ErrorResponse>builder()
                        .success(false)
                        .message("Exception occurred")
                        .errors(response)
                        .build()
        );
    }


    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApplicationResponse<ErrorResponse>> handleInvalidCredentials(InvalidCredentialsException ex, HttpServletRequest http) {

        ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setMessage(ex.getMessage());
        response.setPath(http.getRequestURL().toString());
        response.setTimestamp(Instant.now());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ApplicationResponse.<ErrorResponse>builder()
                        .success(false)
                        .message("Exception occurred")
                        .errors(response)
                        .build()
        );
    }

    @ExceptionHandler(UnAuthenticatedUserException.class)
    public ResponseEntity<ApplicationResponse<ErrorResponse>> handleUnAuthenticatedUser(UnAuthenticatedUserException ex, HttpServletRequest http) {

        ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setMessage(ex.getMessage());
        response.setPath(http.getRequestURL().toString());
        response.setTimestamp(Instant.now());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                ApplicationResponse.<ErrorResponse>builder()
                        .success(false)
                        .message("Exception occurred")
                        .errors(response)
                        .build()
        );
    }

    @ExceptionHandler(RoleMisMatchException.class)
    public ResponseEntity<ApplicationResponse<ErrorResponse>> handleUnAuthenticatedUser(RoleMisMatchException ex, HttpServletRequest http) {

        ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage(ex.getMessage());
        response.setPath(http.getRequestURL().toString());
        response.setTimestamp(Instant.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApplicationResponse.<ErrorResponse>builder()
                        .success(false)
                        .message("Exception occurred")
                        .errors(response)
                        .build()
        );
    }
}
