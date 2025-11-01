package com.dcin.pyramid.service.impl;

import com.dcin.pyramid.exception.UnauthorizedActionException;
import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.entity.Store;
import com.dcin.pyramid.model.entity.Tournament;
import com.dcin.pyramid.repository.TournamentRepository;
import com.dcin.pyramid.service.RegistrationService;
import com.dcin.pyramid.service.TournamentRegisntartionCoordinator;
import com.dcin.pyramid.service.TournamentService;
import com.dcin.pyramid.util.TournamentUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TournamentRegistrationCoordinator implements TournamentRegisntartionCoordinator {
    private final TournamentService tournamentService;
    private final RegistrationService registrationService;
    private final TournamentUtils tournamentUtils;
    private final TournamentRepository tournamentRepository;

    @Override
    public GeneralResponse updateMaxPlayers(Store store, UUID tournamentId, int newMaxPlayers) {
        Tournament tournament = tournamentService.getTournamentById(tournamentId);
        tournamentUtils.checkStoreOrganizer(store, tournament.getOrganizer());
        tournamentUtils.checkTournamentFinished(tournament);
        int activePlayers = (int) tournament.getRegistrations().stream().filter(r -> !r.isReserveList()).count();
        if (newMaxPlayers < activePlayers) {
            throw new UnauthorizedActionException("Can't set maxPlayers to less than current active players (" + activePlayers + ").");
        }
        boolean full = newMaxPlayers == activePlayers;
        tournament.setFullTournament(full);
        tournament.setMaxPlayers(newMaxPlayers);
        tournamentRepository.save(tournament);

        boolean promotion = true;
        while (!tournament.isFullTournament() && promotion) {
            promotion = registrationService.promotePlayerRegistration(tournamentId);
            tournament = tournamentService.getTournamentById(tournamentId);
        }
        return new GeneralResponse("MaxPlayers updated");
    }
}
