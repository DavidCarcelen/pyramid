package com.dcin.pyramid.service.impl;

import com.dcin.pyramid.exception.FullTournamentException;
import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.dto.RegistrationsResponse;
import com.dcin.pyramid.model.entity.Registration;
import com.dcin.pyramid.model.entity.Tournament;
import com.dcin.pyramid.model.entity.User;
import com.dcin.pyramid.repository.RegistrationRepository;
import com.dcin.pyramid.repository.TournamentRepository;
import com.dcin.pyramid.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final RegistrationRepository registrationRepository;
    private final TournamentRepository tournamentRepository;

    @Override
    public RegistrationsResponse checkRegistrationAvailability(User player, UUID tournamentId) {
        if (registrationRepository.existsByPlayerIdAndTournamentId(player.getId(), tournamentId)) {
            throw new IllegalArgumentException("Player already registered for this tournament!");
        }
        Tournament tournament = getTournamentById(tournamentId);
        RegistrationsResponse response = getRegistrations(tournamentId);
        int available = (tournament.getMaxPlayers() - response.totalPlayers());
        return available > 0 ? newRegistration(player, tournamentId) : newReserveListRegistration(player, tournamentId);
    }

    @Override
    public RegistrationsResponse newRegistration(User player, UUID tournamentId) {
        Registration registration = Registration.builder()
                .player(player)
                .tournament(getTournamentById(tournamentId))
                .build();
        registrationRepository.save(registration);
        return getRegistrations(tournamentId);
    }

    @Override
    public RegistrationsResponse newReserveListRegistration(User player, UUID tournamentId) {
        Registration registration = Registration.builder()
                .player(player)
                .tournament(getTournamentById(tournamentId))
                .reserveList(true)
                .build();
        registrationRepository.save(registration);
        return getReserveRegistrations(tournamentId);
    }

    @Override
    public GeneralResponse deleteRegistration(User player, UUID tournamentId) {
        Registration registration = registrationRepository.findByPlayerIdAndTournamentId(player.getId(), tournamentId)
                .orElseThrow(() -> new IllegalArgumentException("Player not registered for this tournament!"));
        registrationRepository.delete(registration);
        //move player from reserve
        return new GeneralResponse("Registration deleted.");
    }

    @Override
    public RegistrationsResponse getRegistrations(UUID tournamentId) {
        List<String> players = registrationRepository.findPlayerNicknamesByTournamentIdAndReserveList(tournamentId, false);
        int totalPlayers = registrationRepository.countPlayersByTournamentIdAndReserveList(tournamentId, false);
        return new RegistrationsResponse("Players list:", players, totalPlayers);
    }
    @Override
    public RegistrationsResponse getReserveRegistrations(UUID tournamentId) {
        List<String> players = registrationRepository.findPlayerNicknamesByTournamentIdAndReserveList(tournamentId, true);
        int totalPlayers = registrationRepository.countPlayersByTournamentIdAndReserveList(tournamentId, true);
        return new RegistrationsResponse("Players in RESERVE list:", players, totalPlayers);
    }

    public Tournament getTournamentById(UUID tournamentId) {
        return tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new IllegalArgumentException("tournament not found."));
    }


}
