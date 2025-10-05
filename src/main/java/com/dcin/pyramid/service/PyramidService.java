package com.dcin.pyramid.service;

import com.dcin.pyramid.model.dto.NewTournamentRequest;
import com.dcin.pyramid.model.dto.TournamentManagementResponse;
import com.dcin.pyramid.model.entity.Tournament;
import com.dcin.pyramid.model.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface PyramidService {
    TournamentManagementResponse createTournament(NewTournamentRequest request, User user);

    TournamentManagementResponse getTournamentsByOrganizer(User organizer, LocalDate date, String message);
    User checkUserName(String name);

    TournamentManagementResponse getAllUpcomingTournaments();

}
