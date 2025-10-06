package com.dcin.pyramid.service;

import com.dcin.pyramid.model.dto.NewTournamentRequest;
import com.dcin.pyramid.model.dto.TournamentResponse;
import com.dcin.pyramid.model.entity.User;

import java.time.LocalDate;
import java.util.UUID;

public interface TournamentService {
    TournamentResponse createTournament(NewTournamentRequest request, User user);

    TournamentResponse getUpcomingTournamentsByStore(UUID storeId);

    void tournamentDateChecker (LocalDate date);

}
