package com.dcin.pyramid.service.impl;

import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.dto.SingleTournamentResponse;
import com.dcin.pyramid.model.dto.TournamentRequest;
import com.dcin.pyramid.model.dto.TournamentsResponse;
import com.dcin.pyramid.model.entity.Tournament;
import com.dcin.pyramid.model.entity.User;
import com.dcin.pyramid.repository.TournamentRepository;
import com.dcin.pyramid.service.TournamentService;
import com.dcin.pyramid.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService {

    private final TournamentRepository tournamentRepository;
    private final UserService userService;

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
                .open(request.open())
                .build();
        tournamentRepository.save(tournament);
        return new SingleTournamentResponse("Tournament created!", tournament);
    }

    @Override
    public TournamentsResponse getUpcomingTournamentsByStore(UUID storeId) {
        List<Tournament> tournaments;
        if (storeId != null) {
            userService.checkId(storeId);
            tournaments = tournamentRepository.findByOrganizerIdAndStartDateTimeAfter(storeId, LocalDate.now());
        } else {
            tournaments = tournamentRepository.findAllByStartDateTimeAfter(LocalDateTime.now());
        }
        return new TournamentsResponse("Upcoming tournaments: ", tournaments);
    }

    @Override
    public void tournamentIdChecker(UUID tournamentId) {
        if (!tournamentRepository.existsById(tournamentId)) {
            throw new IllegalArgumentException("Tournament not found");
        }
    }

    @Override
    public SingleTournamentResponse updateTournament(TournamentRequest request, User user, UUID tournamentId) {
        Tournament tournamentToUpdate = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new IllegalArgumentException("Tournament not found"));
        tournamentToUpdate.setTournamentName(request.tournamentName());
        tournamentToUpdate.setStartDateTime(request.startDateTime());
        tournamentToUpdate.setMaxPlayers(request.maxPlayers());
        tournamentToUpdate.setFormat(request.format());
        tournamentToUpdate.setExtraInfo(request.extraInfo());
        tournamentToUpdate.setPrice(request.price());
        tournamentToUpdate.setOpen(request.open());
        tournamentRepository.save(tournamentToUpdate);

        return new SingleTournamentResponse("Tournament updated", tournamentToUpdate);
    }

    @Override
    public GeneralResponse deleteTournament(User user, UUID tournamentId) {
        Tournament tournamentToDelete = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new IllegalArgumentException("tournament not found"));
        tournamentRepository.delete(tournamentToDelete);
        return new GeneralResponse("Tournament deleted");
    }

    @Override
    public TournamentsResponse getAllTournaments(UUID userId) {
        return new TournamentsResponse("Tournaments history: ", tournamentRepository.findByOrganizerId(userId));
    }
    @Override
    public Tournament getTournamentById(UUID tournamentId) {
        return tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new IllegalArgumentException("tournament not found."));
    }
    @Override
    public void updatePrizeMoneyAndSpotsAvailable(UUID tournamentId, int totalPlayers) {
        Tournament tournament = getTournamentById(tournamentId);
        tournament.setPrizeMoney(tournament.getPrice().multiply(new BigDecimal(totalPlayers)));
        tournament.setFullTournament(totalPlayers >= tournament.getMaxPlayers());
        tournamentRepository.save(tournament);
    }
    @Override
    public void setTournamentNotFull(UUID tournamentId) {
        Tournament tournament = getTournamentById(tournamentId);
        tournament.setFullTournament(false);
        tournamentRepository.save(tournament);
    }

    @Override
    public void checkTournamentOpen(Tournament tournament){
        if(LocalDateTime.now().isAfter(tournament.getStartDateTime())){
            tournament.setOpen(false);
            tournamentRepository.save(tournament);
        }
        if(!tournament.isOpen()){
            throw new IllegalArgumentException("Closed registrations for this tournament.");
        }
    }


}
