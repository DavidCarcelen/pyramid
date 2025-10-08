package com.dcin.pyramid.service.impl;

import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.dto.TournamentResponse;
import com.dcin.pyramid.model.entity.Registration;
import com.dcin.pyramid.model.entity.Tournament;
import com.dcin.pyramid.model.entity.User;
import com.dcin.pyramid.repository.RegistrationRepository;
import com.dcin.pyramid.repository.TournamentRepository;
import com.dcin.pyramid.service.RegistrationService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final RegistrationRepository registrationRepository;
    private final TournamentRepository tournamentRepository;
    @Override
    public void checkRegistration(UUID playerId, UUID tournamentId) {
        if(registrationRepository.existsByPlayerIdAndTournamentId(playerId, tournamentId)){
            throw new IllegalArgumentException("Player already registered for this tournament!");
        }
    }

    @Override
    public GeneralResponse newRegistration(User player, UUID tournamentId) {
        checkRegistration(player.getId(), tournamentId);
        Registration registration = Registration.builder()
                .player(player)
                .tournament(getTournamentById(tournamentId))
                .build();
        registrationRepository.save(registration);
        return new GeneralResponse("Registered to tournament: " + registration.getTournament().getTournamentName());
    }

    @Override
    public GeneralResponse deleteRegistration(User player, UUID tournamentId) {
        Registration registration = registrationRepository.findByPlayerIdAndTournamentId(player.getId(), tournamentId)
                .orElseThrow(()-> new IllegalArgumentException("Player not registered for this tournament!"));
            registrationRepository.delete(registration);
        return new GeneralResponse("Registration deleted.");
    }

    public Tournament getTournamentById (UUID tournamentId){
        return  tournamentRepository.findById(tournamentId)
                .orElseThrow(()->new IllegalArgumentException("tournament not found." ));
    }
}
