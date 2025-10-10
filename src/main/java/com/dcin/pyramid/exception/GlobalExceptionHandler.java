package com.dcin.pyramid.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> userNotFoundException(UserNotFoundException exception, WebRequest request){
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                exception.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public ResponseEntity<ErrorMessage> userAlreadyRegisteredException(UserAlreadyRegisteredException exception, WebRequest request){
        ErrorMessage message = new ErrorMessage(
                HttpStatus.CONFLICT.value(),
                new Date(),
                exception.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<ErrorMessage>(message, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorMessage> badCredentialsException(BadCredentialsException exception, WebRequest request){
        ErrorMessage message = new ErrorMessage(
                HttpStatus.UNAUTHORIZED.value(),
                new Date(),
                exception.getMessage(),
                request.getDescription(false)

        );
        return new ResponseEntity<ErrorMessage>(message, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(FullTournamentException.class)
    public ResponseEntity<ErrorMessage> fullTournamentException(FullTournamentException exception, WebRequest request){
        ErrorMessage message = new ErrorMessage(
                HttpStatus.CONFLICT.value(),
                new Date(),
                exception.getMessage(),
                request.getDescription(false)

        );
        return new ResponseEntity<ErrorMessage>(message, HttpStatus.CONFLICT);
    }
}
