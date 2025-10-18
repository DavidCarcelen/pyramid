package com.dcin.pyramid.exception;

public class ClosedTournamentException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "Closed registrations for this tournament.";

    public ClosedTournamentException(){
        super(DEFAULT_MESSAGE);
    }
}
