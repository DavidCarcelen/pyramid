package com.dcin.pyramid.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> userNotFoundException(EntityNotFoundException exception, WebRequest request){
        return buildErrorResponse(exception, HttpStatus.NOT_FOUND, request);

    }
    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public ResponseEntity<ErrorMessage> userAlreadyRegisteredException(UserAlreadyRegisteredException exception, WebRequest request){
        return buildErrorResponse(exception, HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorMessage> badCredentialsException(BadCredentialsException exception, WebRequest request){
        return buildErrorResponse(exception, HttpStatus.UNAUTHORIZED, request);
    }
    @ExceptionHandler(FullTournamentException.class)
    public ResponseEntity<ErrorMessage> fullTournamentException(FullTournamentException exception, WebRequest request){

        return buildErrorResponse(exception, HttpStatus.CONFLICT, request);
    }
    @ExceptionHandler(ClosedTournamentException.class)
    public ResponseEntity<ErrorMessage> closeTournamentException(ClosedTournamentException exception, WebRequest request){
        return buildErrorResponse(exception, HttpStatus.CONFLICT, request);
    }
    @ExceptionHandler(RoleException.class)
    public ResponseEntity<ErrorMessage> roleException (RoleException exception, WebRequest request){
        return buildErrorResponse(exception,HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(InvalidJwtException.class)
    public ResponseEntity<ErrorMessage> invalidJwtException (InvalidJwtException exception, WebRequest request){
        return buildErrorResponse(exception, HttpStatus.UNAUTHORIZED, request);
    }

    private ResponseEntity<ErrorMessage> buildErrorResponse(Exception ex, HttpStatus status, WebRequest request){
        ErrorMessage errorMessage = ErrorMessage.builder()
                .status(status.value())
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .path(request.getDescription(false))
                .build();
        return new ResponseEntity<>(errorMessage, status);
    }
}
