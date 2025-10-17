package com.dcin.pyramid.exception;

public class BadCredentialsException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "Bad credentials.";
    public BadCredentialsException(){
        super(DEFAULT_MESSAGE);
    }
    public BadCredentialsException (String message){

        super(message != null ? message : DEFAULT_MESSAGE);
    }
}
