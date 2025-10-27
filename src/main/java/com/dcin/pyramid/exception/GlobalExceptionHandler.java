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
    public static ErrorMessage buildError(HttpStatus status, String message, String path){
        return new ErrorMessage(
                status.value(),
                LocalDateTime.now(),
                message,
                path
        );
    }
    private ResponseEntity<ErrorMessage> buildErrorResponse(Exception ex, HttpStatus status, WebRequest request){
        ErrorMessage errorMessage = buildError(status, ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorMessage, status);
    }
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
    public ResponseEntity<ErrorMessage> closedTournamentException(ClosedTournamentException exception, WebRequest request){
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

    @ExceptionHandler(UnauthorizedActionException.class)
    public ResponseEntity<ErrorMessage> unauthorizedActionException (UnauthorizedActionException exception, WebRequest request){
        return buildErrorResponse(exception, HttpStatus.UNAUTHORIZED, request);
    }
    @ExceptionHandler(InvalidTeamOperationException.class)
    public ResponseEntity<ErrorMessage> invalidTeamOperationException (InvalidTeamOperationException exception, WebRequest request){
        return buildErrorResponse(exception, HttpStatus.CONFLICT, request);
    }


}
