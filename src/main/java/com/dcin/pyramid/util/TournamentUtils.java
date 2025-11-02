package com.dcin.pyramid.util;

import com.dcin.pyramid.exception.ClosedTournamentException;
import com.dcin.pyramid.exception.UnauthorizedActionException;
import com.dcin.pyramid.model.entity.Store;
import com.dcin.pyramid.model.entity.Tournament;
import org.springframework.stereotype.Component;

@Component
public class TournamentUtils {

    public void checkTournamentFinished(Tournament tournament) {
        if (tournament.isFinished()) {
            throw new UnauthorizedActionException("Tournament finished, can't make operations."); // change to defaultmessage
        }
    }
    public void checkTournamentOpen(Tournament tournament) {
        if (!tournament.isOpenTournament()) {
            throw new ClosedTournamentException();
        }
    }
    public void checkStoreOrganizer(Store store, Store organizer){
        if(!store.getId().equals(organizer.getId())){
            throw new UnauthorizedActionException("Only the organizer can make this operation.");
        }
    }
}
