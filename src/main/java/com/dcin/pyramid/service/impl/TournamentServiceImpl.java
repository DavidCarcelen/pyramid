package com.dcin.pyramid.service.impl;

import com.dcin.pyramid.exception.EntityNotFoundException;
import com.dcin.pyramid.exception.UnauthorizedActionException;
import com.dcin.pyramid.model.dto.*;
import com.dcin.pyramid.model.entity.Tournament;
import com.dcin.pyramid.model.entity.User;
import com.dcin.pyramid.model.mappers.TournamentMapper;
import com.dcin.pyramid.repository.TournamentRepository;
import com.dcin.pyramid.service.TournamentService;
import com.dcin.pyramid.util.TournamentUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService {

    private final TournamentRepository tournamentRepository;
    private final TournamentMapper tournamentMapper;
    private final TournamentUtils tournamentUtils;

    @Override
    public Tournament getTournamentById(UUID tournamentId) {
        return tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new EntityNotFoundException("Tournament not found."));
    }

    @Transactional
    @Override
    public SingleTournamentResponse createTournament(TournamentRequest request, User organizer) {
        Tournament tournament = Tournament.builder()
                .tournamentName(request.tournamentName())
                .startDateTime(request.startDateTime())
                .maxPlayers(request.maxPlayers())
                .format(request.format())
                .extraInfo(request.extraInfo())
                .price(request.price())
                .organizer(organizer)
                .openTournament(request.openTournament())
                .companionCode(request.companionCode())
                .build();
        tournamentRepository.save(tournament);
        return new SingleTournamentResponse("Tournament created!", tournamentMapper.toDTO(tournament));
    }

    @Override
    public TournamentsResponse getUpcomingTournamentsByStore(UUID storeId) {
        List<Tournament> tournaments;
        if (storeId != null) {
            tournaments = tournamentRepository.findByOrganizerIdAndStartDateTimeAfter(storeId, LocalDateTime.now());
        } else {
            tournaments = tournamentRepository.findAllByStartDateTimeAfter(LocalDateTime.now());
        }
        List<TournamentInfoDTO> dtoList = tournaments.stream()
                .map(tournamentMapper::toDTO)
                .toList();
        return new TournamentsResponse("Upcoming tournaments: ", dtoList);
    }


    @Override
    public SingleTournamentResponse updateTournament(TournamentRequest request, User user, UUID tournamentId) {
        Tournament tournamentToUpdate = getTournamentById(tournamentId);
        tournamentUtils.checkUserOrganizer(user, tournamentToUpdate.getOrganizer());
        tournamentToUpdate.setTournamentName(request.tournamentName());
        tournamentToUpdate.setStartDateTime(request.startDateTime());
        tournamentToUpdate.setMaxPlayers(request.maxPlayers()); // mirar bien esto, cuidado con el metodo y registrations
        tournamentToUpdate.setFormat(request.format());
        tournamentToUpdate.setExtraInfo(request.extraInfo());
        tournamentToUpdate.setPrice(request.price());
        tournamentToUpdate.setOpenTournament(request.openTournament());
        tournamentToUpdate.setCompanionCode(request.companionCode());
        tournamentRepository.save(tournamentToUpdate);

        return new SingleTournamentResponse("Tournament updated", tournamentMapper.toDTO(tournamentToUpdate));
    }

    @Override
    public GeneralResponse deleteTournament(User user, UUID tournamentId) {
        Tournament tournamentToDelete = getTournamentById(tournamentId);
        tournamentUtils.checkUserOrganizer(user, tournamentToDelete.getOrganizer());
        tournamentRepository.delete(tournamentToDelete);
        return new GeneralResponse("Tournament deleted");
    }

    @Override
    public TournamentsResponse getAllTournaments(UUID userId) {
        List<TournamentInfoDTO> dtoList = tournamentRepository.findByOrganizerId(userId).stream()
                .map(tournamentMapper::toDTO)
                .toList();
        return new TournamentsResponse("Tournaments history: ", dtoList);
    }



    @Override
    public void updatePrizeMoneyAndSpotsAvailable(UUID tournamentId, int activePlayers) {
        Tournament tournament = getTournamentById(tournamentId);
        tournament.setPrizeMoney(tournament.getPrice().multiply(new BigDecimal(activePlayers)));
        tournament.setFullTournament(activePlayers >= tournament.getMaxPlayers());
        tournamentRepository.save(tournament);
    }


    @Override
    public GeneralResponse openCloseTournament(User user, UUID tournamentId, boolean state) {
        Tournament tournament = getTournamentById(tournamentId);
        tournamentUtils.checkUserOrganizer(user, tournament.getOrganizer());
        tournament.setOpenTournament(state);
        tournamentRepository.save(tournament);
        String message = state ? "open." : "closed.";
        return new GeneralResponse("Tournament registrations " + message);
    }

    @Override
    public SingleTournamentResponse getOneTournament(UUID tournamentId) {
        Tournament tournament = getTournamentById(tournamentId);
        return new SingleTournamentResponse("Tournament", tournamentMapper.toDTO(tournament));
    }

    @Override
    public GeneralResponse addCompanionCode(User user, UUID tournamentId, String companionCode) {
        Tournament tournament = getTournamentById(tournamentId);
        tournamentUtils.checkUserOrganizer(user, tournament.getOrganizer());
        tournamentUtils.checkTournamentFinished(tournament);
        tournament.setCompanionCode(companionCode);
        tournamentRepository.save(tournament);
        return new GeneralResponse("Companion code added successfully  ");
    }

    @Override
    public GeneralResponse finishTournament(User user, UUID tournamentId) {
        Tournament tournament = getTournamentById(tournamentId);
        tournamentUtils.checkUserOrganizer(user, tournament.getOrganizer());
        if (tournament.getStartDateTime().isAfter(LocalDateTime.now())) {
            throw new UnauthorizedActionException("Can't finish this tournament, It hasn't started yet");
        }
        tournament.setFinished(true);
        tournament.setOpenTournament(false);
        tournamentRepository.save(tournament);
        return new GeneralResponse("Tournament finished, total prize = " + tournament.getPrizeMoney());
    }

}
