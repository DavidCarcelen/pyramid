package com.dcin.pyramid.exception;

public class UserNotFoundException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "User not found.";
    public UserNotFoundException(){
        super(DEFAULT_MESSAGE);
    }
    public UserNotFoundException(String message){

        super(message != null? message : DEFAULT_MESSAGE);
    }
}
