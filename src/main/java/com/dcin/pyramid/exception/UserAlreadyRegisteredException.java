package com.dcin.pyramid.exception;

public class UserAlreadyRegisteredException extends RuntimeException{
    public UserAlreadyRegisteredException(String message){
        super(message);
    }
}
