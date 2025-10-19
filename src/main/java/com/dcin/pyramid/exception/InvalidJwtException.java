package com.dcin.pyramid.exception;

public class InvalidJwtException extends RuntimeException{

    public InvalidJwtException(String message){
        super(message);
    }
}
