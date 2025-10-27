package com.dcin.pyramid.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.ObjectMapper;

import static com.dcin.pyramid.exception.GlobalExceptionHandler.buildError;

@Component
public class SecurityExceptionHandler implements AuthenticationEntryPoint, AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        ErrorMessage message = buildError(
                HttpStatus.UNAUTHORIZED,
                "Authentication failed: " + authException.getMessage(),
                request.getRequestURI()
        );
        writeJsonResponse(response, HttpStatus.UNAUTHORIZED, message);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException {
        ErrorMessage message = buildError(
                HttpStatus.FORBIDDEN,
                "Access denied: " + accessDeniedException.getMessage(),
                request.getRequestURI()
        );
        writeJsonResponse(response, HttpStatus.FORBIDDEN, message);
    }

    private void writeJsonResponse(HttpServletResponse response, HttpStatus status, ErrorMessage message)
            throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
        objectMapper.writeValue(response.getOutputStream(), message);
    }
}

