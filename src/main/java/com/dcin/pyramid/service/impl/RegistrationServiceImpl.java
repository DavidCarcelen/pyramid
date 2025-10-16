package com.dcin.pyramid.service.impl;

import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.dto.RegistrationInfoDTO;
import com.dcin.pyramid.model.dto.RegistrationsResponse;
import com.dcin.pyramid.model.entity.Registration;
import com.dcin.pyramid.model.entity.Tournament;
import com.dcin.pyramid.model.entity.User;
import com.dcin.pyramid.repository.RegistrationRepository;
import com.dcin.pyramid.service.RegistrationService;
import com.dcin.pyramid.service.TournamentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final RegistrationRepository registrationRepository;
    private final TournamentService tournamentService;

    @Override
    public RegistrationsResponse handleRegistration(User player, UUID tournamentId) {
        if (registrationRepository.existsByPlayerIdAndTournamentId(player.getId(), tournamentId)) {
            throw new IllegalArgumentException("Player already registered for this tournament!");
        }
        Tournament tournament = tournamentService.getTournamentById(tournamentId);
        RegistrationsResponse response = tournament.isFullTournament() ?
                newRegistration(player, tournament, true) :
                newRegistration(player, tournament, false);
        tournamentService.updatePrizeMoneyAndSpotsAvailable(tournamentId, response.totalPlayers());
        return response;
    }

    @Override
    public RegistrationsResponse newRegistration(User player, Tournament tournament, boolean reserveList) {
        Registration registration = Registration.builder()
                .player(player)
                .tournament(tournament)
                .reserveList(reserveList)
                .build();
        registrationRepository.save(registration);
        return getAllRegistrations(tournament.getId());
    }

    @Override
    public GeneralResponse deleteRegistration(User player, UUID tournamentId) {
        Registration registration = registrationRepository.findByPlayerIdAndTournamentId(player.getId(), tournamentId)
                .orElseThrow(() -> new IllegalArgumentException("Player not registered for this tournament!"));
        registrationRepository.delete(registration);
        if (!registration.isReserveList()) {
            promotePlayerRegistration(tournamentId);

        }
        return new GeneralResponse("Registration deleted.");
    }

    @Override
    public RegistrationsResponse getAllRegistrations(UUID tournamentId) {
        List<RegistrationInfoDTO> registrations =
                registrationRepository.findAllRegistrationsByTournamentIdOrdered(tournamentId);
        return new RegistrationsResponse("All registrations:", registrations, registrations.size());
    }

    @Transactional
    public void promotePlayerRegistration(UUID tournamentId) {
        Optional<Registration> firstReserve = registrationRepository.findFirstByTournamentIdAndReserveListTrueOrderByRegisteredAtAsc(tournamentId);
        if (firstReserve.isPresent()) {
            Registration promoted = firstReserve.get();
            promoted.setReserveList(false);
            promoted.setRegisteredAt(LocalDateTime.now());
            registrationRepository.save(promoted);
        } else {
            tournamentService.setTournamentNotFull(tournamentId);
        }

    }

}
