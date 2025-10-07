package com.dcin.pyramid.service;

import com.dcin.pyramid.model.dto.TournamentRequest;
import com.dcin.pyramid.model.dto.TournamentResponse;
import com.dcin.pyramid.model.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;

public interface TournamentService {
    TournamentResponse createTournament(TournamentRequest request, User user);

    TournamentResponse getUpcomingTournamentsByStore(UUID storeId);

    void tournamentDateChecker (LocalDateTime date);

    TournamentResponse updateTournament(TournamentRequest request, User user);

    TournamentResponse deleteTournament(User user);

}
