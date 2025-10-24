package com.dcin.pyramid.util;

import com.dcin.pyramid.exception.ClosedTournamentException;
import com.dcin.pyramid.exception.UnauthorizedActionException;
import com.dcin.pyramid.model.entity.Tournament;
import com.dcin.pyramid.model.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

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
    public void checkUserOrganizer(User user, User organizer){
        if(!user.equals(organizer)){
            throw new UnauthorizedActionException("Only the organizer can make this operation.");
        }
    }
}
