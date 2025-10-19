package com.dcin.pyramid.service;

import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.dto.SingleTournamentResponse;
import com.dcin.pyramid.model.dto.TournamentRequest;
import com.dcin.pyramid.model.dto.TournamentsResponse;
import com.dcin.pyramid.model.entity.Tournament;
import com.dcin.pyramid.model.entity.User;

import java.util.UUID;

public interface TournamentService {
    SingleTournamentResponse createTournament(TournamentRequest request, User user);

    TournamentsResponse getUpcomingTournamentsByStore(UUID storeId);

    SingleTournamentResponse updateTournament(TournamentRequest request, User user, UUID tournamentId);

    GeneralResponse deleteTournament(User user, UUID tournamentId);
    TournamentsResponse getAllTournaments(UUID userId);
    Tournament getTournamentById(UUID tournamentId);
    void updatePrizeMoneyAndSpotsAvailable(UUID tournamentId, int activePlayers);
    void setTournamentNotFull(UUID tournamentId);
    void checkTournamentOpen(Tournament tournament);
    GeneralResponse openCloseTournament(User user,UUID tournamentId, boolean state);

}
