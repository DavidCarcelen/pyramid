package com.dcin.pyramid.exception;

public class FullTournamentException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "Full tournament!!!";
    public FullTournamentException(){
        super(DEFAULT_MESSAGE);
    }
    public FullTournamentException(String message){

        super(message != null ? message : DEFAULT_MESSAGE);
    }

}
