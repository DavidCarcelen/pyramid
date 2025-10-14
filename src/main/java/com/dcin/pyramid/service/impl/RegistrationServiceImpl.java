package com.dcin.pyramid.service.impl;

import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.dto.RegistrationsResponse;
import com.dcin.pyramid.model.entity.Registration;
import com.dcin.pyramid.model.entity.Tournament;
import com.dcin.pyramid.model.entity.User;
import com.dcin.pyramid.repository.RegistrationRepository;
import com.dcin.pyramid.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final RegistrationRepository registrationRepository;
    private final TournamentServiceImpl tournamentService;

    @Override
    public RegistrationsResponse checkRegistrationAvailability(User player, UUID tournamentId) {
        if (registrationRepository.existsByPlayerIdAndTournamentId(player.getId(), tournamentId)) {
            throw new IllegalArgumentException("Player already registered for this tournament!");
        }
        Tournament tournament = tournamentService.getTournamentById(tournamentId);
        return tournament.isFull() ? newReserveListRegistration(player, tournament) : newRegistration(player, tournament);
    }

    @Override
    public RegistrationsResponse newRegistration(User player, Tournament tournament) {
        Registration registration = Registration.builder()
                .player(player)
                .tournament(tournament)
                .build();
        registrationRepository.save(registration);
        return getRegistrations(tournament.getId());
    }

    @Override
    public RegistrationsResponse newReserveListRegistration(User player, Tournament tournament) {
        Registration registration = Registration.builder()
                .player(player)
                .tournament(tournament)
                .reserveList(true)
                .build();
        registrationRepository.save(registration);
        return getReserveRegistrations(tournament.getId());
    }

    @Override
    public GeneralResponse deleteRegistration(User player, UUID tournamentId) {
        Registration registration = registrationRepository.findByPlayerIdAndTournamentId(player.getId(), tournamentId)
                .orElseThrow(() -> new IllegalArgumentException("Player not registered for this tournament!"));
        registrationRepository.delete(registration);
        if(!registration.isReserveList()){
            promotePlayerRegistration(tournamentId);

        }
        return new GeneralResponse("Registration deleted.");
    }

    @Override
    public RegistrationsResponse getRegistrations(UUID tournamentId) {
        List<String> players = registrationRepository.findPlayerNicknamesByTournamentIdAndReserveList(tournamentId, false);
        int totalPlayers = registrationRepository.countPlayersByTournamentIdAndReserveList(tournamentId, false);
        tournamentService.updatePrizeMoneyAndSpotsAvailble(tournamentId, totalPlayers);
        return new RegistrationsResponse("Players list:", players, totalPlayers);
    }
    @Override
    public RegistrationsResponse getReserveRegistrations(UUID tournamentId) {
        List<String> players = registrationRepository.findPlayerNicknamesByTournamentIdAndReserveList(tournamentId, true);
        int totalPlayers = registrationRepository.countPlayersByTournamentIdAndReserveList(tournamentId, true);
        return new RegistrationsResponse("Players in RESERVE list:", players, totalPlayers);
    }

    public void promotePlayerRegistration (UUID tournamentId){
        Optional<Registration> firstReserve = registrationRepository.findFirstByTournamentIdAndReserveListTrueOrderByRegisteredAtAsc(tournamentId);
      firstReserve.ifPresent(reserve -> {
          reserve.setReserveList(false);
          registrationRepository.save(reserve);
      });

    }


}
