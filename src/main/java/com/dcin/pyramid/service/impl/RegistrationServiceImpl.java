package com.dcin.pyramid.service.impl;

import com.dcin.pyramid.exception.BadCredentialsException;
import com.dcin.pyramid.exception.UnauthorizedActionException;
import com.dcin.pyramid.exception.UserAlreadyRegisteredException;
import com.dcin.pyramid.exception.EntityNotFoundException;
import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.dto.RegistrationInfoDTO;
import com.dcin.pyramid.model.dto.RegistrationsResponse;
import com.dcin.pyramid.model.entity.Registration;
import com.dcin.pyramid.model.entity.Tournament;
import com.dcin.pyramid.model.entity.User;
import com.dcin.pyramid.model.mappers.RegistrationMapper;
import com.dcin.pyramid.repository.RegistrationRepository;
import com.dcin.pyramid.service.RegistrationService;
import com.dcin.pyramid.service.TournamentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final RegistrationRepository registrationRepository;
    private final TournamentService tournamentService;
    private final RegistrationMapper registrationMapper;

    @Override
    public RegistrationsResponse handleRegistration(User player, UUID tournamentId) {
        if (registrationRepository.existsByPlayerIdAndTournamentId(player.getId(), tournamentId)) {
            throw new UserAlreadyRegisteredException("Player already registered for this tournament!");
        }
        Tournament tournament = tournamentService.getTournamentById(tournamentId);
        tournamentService.checkTournamentOpen(tournament);
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
        return getAllRegistrations(tournament.getId());/// cuidad getDTO
    }
    @Transactional
    @Override
    public GeneralResponse deleteRegistration(User user, UUID registrationId) {
        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new EntityNotFoundException("Registration not found!"));
        Tournament tournament = registration.getTournament();

        if (!registration.getPlayer().equals(user) && !tournament.getOrganizer().equals(user)) {
            throw new UnauthorizedActionException("Can't delete others registrations.");
        }
        tournamentService.checkTournamentOpen(tournament);
        registrationRepository.delete(registration);
        if (!registration.isReserveList()) {
            promotePlayerRegistration(tournament.getId());

        }
        return new GeneralResponse(registration.getPlayer().getNickname() + " registration deleted.");
    } //cuidado en el proceso hay doble tournament save!!!

    @Override
    public RegistrationsResponse getAllRegistrations(UUID tournamentId) {
        List<RegistrationInfoDTO> registrations =
                registrationRepository.findAllRegistrationsByTournamentIdOrdered(tournamentId);
        int activePlayers = registrationRepository.countActivePlayersByTournamentId(tournamentId);
        return new RegistrationsResponse("All registrations:", registrations, activePlayers);
    }

    @Transactional
    @Override
    public void promotePlayerRegistration(UUID tournamentId) {
        Optional<Registration> firstReserve = registrationRepository.findFirstByTournamentIdAndReserveListTrueOrderByRegisteredAtAsc(tournamentId);
        if (firstReserve.isPresent()) {
            Registration promoted = firstReserve.get();
            promoted.setReserveList(false);
            promoted.setRegisteredAt(LocalDateTime.now());
            registrationRepository.save(promoted);
        } else {
            tournamentService.setTournamentNotFull(tournamentId);
            int activePlayers = registrationRepository.countActivePlayersByTournamentId(tournamentId);
            tournamentService.updatePrizeMoneyAndSpotsAvailable(tournamentId, activePlayers);
        }

    }

    @Override
    public GeneralResponse markAsPaid(User store, UUID registrationId) {
        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new EntityNotFoundException("Registration not found"));
        Tournament tournament = registration.getTournament();
        if (!tournament.getOrganizer().equals(store)) {
            throw new UnauthorizedActionException("Only the organizer can mark registrations as paid.");
        }
        if (registration.isPaid()) {
            return new GeneralResponse(registration.getPlayer().getNickname() + " is already marked as paid.");
        }
        registration.setPaid(true);
        registrationRepository.save(registration);
        return new GeneralResponse(registration.getPlayer().getNickname() + " registration marked as paid.");
    }

}
