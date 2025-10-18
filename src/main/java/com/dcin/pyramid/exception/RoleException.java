package com.dcin.pyramid.exception;

public class RoleException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "Only for: ";
    public RoleException(String role){
        super(DEFAULT_MESSAGE + role);
    }
}
