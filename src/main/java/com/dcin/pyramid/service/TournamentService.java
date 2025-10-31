package com.dcin.pyramid.service;

import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.dto.tournament.SingleTournamentResponse;
import com.dcin.pyramid.model.dto.tournament.TournamentRequest;
import com.dcin.pyramid.model.dto.tournament.TournamentsResponse;
import com.dcin.pyramid.model.entity.Store;
import com.dcin.pyramid.model.entity.Tournament;
import com.dcin.pyramid.model.entity.User;

import java.util.UUID;

public interface TournamentService {
    SingleTournamentResponse createTournament(Store store, TournamentRequest request);

    TournamentsResponse getUpcomingTournamentsByStore(UUID storeId);

    SingleTournamentResponse updateTournament(Store store, TournamentRequest request, UUID tournamentId);

    GeneralResponse deleteTournament(Store store, UUID tournamentId);

    TournamentsResponse getAllTournaments(UUID storeId);

    void updatePrizeMoneyAndSpotsAvailable(UUID tournamentId, int activePlayers);

    GeneralResponse openCloseTournament(Store store, UUID tournamentId, boolean state);

    SingleTournamentResponse getOneTournament(UUID tournamentId);

    GeneralResponse addCompanionCode(Store store, UUID tournamentId, String companionCode);

    GeneralResponse finishTournament(Store store, UUID tournamentId);

    Tournament getTournamentById(UUID tournamentId);


}
