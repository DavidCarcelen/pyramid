package com.dcin.pyramid.service.impl;

import com.dcin.pyramid.exception.UnauthorizedActionException;
import com.dcin.pyramid.exception.UserAlreadyRegisteredException;
import com.dcin.pyramid.exception.EntityNotFoundException;
import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.dto.registration.RegistrationInfoDTO;
import com.dcin.pyramid.model.dto.registration.RegistrationsResponse;
import com.dcin.pyramid.model.entity.*;
import com.dcin.pyramid.repository.RegistrationRepository;
import com.dcin.pyramid.service.RegistrationService;
import com.dcin.pyramid.service.TournamentService;
import com.dcin.pyramid.util.TournamentUtils;
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
    private final TournamentUtils tournamentUtils;

    private Registration getRegistrationById(UUID registrationId) {
        return registrationRepository.findById(registrationId)
                .orElseThrow(() -> new EntityNotFoundException("Registration not found."));
    }

    @Override
    public RegistrationsResponse handleRegistration(Player player, UUID tournamentId) {
        Tournament tournament = tournamentService.getTournamentById(tournamentId);
        tournamentUtils.checkTournamentFinished(tournament);
        tournamentUtils.checkTournamentOpen(tournament);
        if (registrationRepository.existsByPlayerIdAndTournamentId(player.getId(), tournamentId)) {
            throw new UserAlreadyRegisteredException("Player already registered for this tournament!");
        }
        RegistrationsResponse response = tournament.isFullTournament() ?
                newRegistration(player, tournament, true) :
                newRegistration(player, tournament, false);
        tournamentService.updatePrizeMoneyAndSpotsAvailable(tournamentId, response.totalPlayers());
        return response;
    }

    @Override
    public RegistrationsResponse newRegistration(Player player, Tournament tournament, boolean reserveList) {
        Registration registration = Registration.builder()
                .player(player)
                .tournament(tournament)
                .reserveList(reserveList)
                .build();
        registrationRepository.save(registration);
        return getAllRegistrations(tournament.getId());
    }

    @Transactional
    @Override
    public GeneralResponse deleteRegistration(User user, UUID registrationId) {
        boolean promotion = false;
        Registration registration = getRegistrationById(registrationId);
        Tournament tournament = registration.getTournament();
        tournamentUtils.checkTournamentOpen(tournament);
        tournamentUtils.checkTournamentFinished(tournament);

        if (!registration.getPlayer().getId().equals(user.getId()) && !tournament.getOrganizer().getId().equals(user.getId())) {
            throw new UnauthorizedActionException("Can't delete others registrations.");
        }
        registrationRepository.delete(registration);
        if (!registration.isReserveList()) {
            promotion = promotePlayerRegistration(tournament.getId());
        }
        String message = promotion ? " registration deleted and first player on reserve list promoted." : " registration deleted.";
        return new GeneralResponse(registration.getPlayer().getNickname() + message);
    }

    @Override
    public RegistrationsResponse getAllRegistrations(UUID tournamentId) {
        List<RegistrationInfoDTO> registrations =
                registrationRepository.findAllRegistrationsByTournamentIdOrdered(tournamentId);
        int activePlayers = registrationRepository.countActivePlayersByTournamentId(tournamentId);
        int reservePlayers = registrationRepository.countReserveListPlayersByTournamentId(tournamentId);
        boolean full = reservePlayers > 0;
        return new RegistrationsResponse("All registrations:", registrations, activePlayers, reservePlayers, full);
    }

    @Transactional
    @Override
    public boolean promotePlayerRegistration(UUID tournamentId) {
        boolean promotion;
        Optional<Registration> firstReserve = registrationRepository.findFirstByTournamentIdAndReserveListTrueOrderByRegistrationTimeAsc(tournamentId);
        if (firstReserve.isPresent()) {
            Registration promoted = firstReserve.get();
            promoted.setReserveList(false);
            promoted.setRegistrationTime(LocalDateTime.now());
            registrationRepository.save(promoted);
            promotion = true;
        } else {
            promotion = false;
        }
        int activePlayers = registrationRepository.countActivePlayersByTournamentId(tournamentId);
        tournamentService.updatePrizeMoneyAndSpotsAvailable(tournamentId, activePlayers);
        return promotion;
    }

    @Override
    public GeneralResponse markAsPaid(Store store, UUID registrationId) {
        Registration registration = getRegistrationById(registrationId);
        Tournament tournament = registration.getTournament();
        tournamentUtils.checkStoreOrganizer(store, tournament.getOrganizer());
        String message;
        if (registration.isPaid()) {
            message = " is already marked as paid.";
        } else {
            registration.setPaid(true);
            registrationRepository.save(registration);
            message = " registration marked as paid.";
        }
        return new GeneralResponse(registration.getPlayer().getNickname() + message);
    }

}
