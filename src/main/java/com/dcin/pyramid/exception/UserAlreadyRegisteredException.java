package com.dcin.pyramid.exception;

public class UserAlreadyRegisteredException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "User already registered.";

    public  UserAlreadyRegisteredException(){
        super(DEFAULT_MESSAGE);
    }
    public UserAlreadyRegisteredException(String message){

        super(message != null? message : DEFAULT_MESSAGE);
    }
}
