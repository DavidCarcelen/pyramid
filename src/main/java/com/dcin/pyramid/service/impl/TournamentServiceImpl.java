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
import java.math.BigInteger;
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


    public void tournamentIdChecker(UUID tournamentId) {
        if (!tournamentRepository.existsById(tournamentId)) {
            throw new IllegalArgumentException("Tournament not found");
        }
    }

    @Override
    public SingleTournamentResponse updateTournament(TournamentRequest request, User user, UUID tournamentId) {
        Tournament tournamentToUpdate = tournamentRepository.findById(tournamentId)
                .orElseThrow(()-> new IllegalArgumentException("Tournament not found"));
        tournamentToUpdate.setTournamentName(request.tournamentName());
        tournamentToUpdate.setStartDateTime(request.startDateTime());
        tournamentToUpdate.setMaxPlayers(request.maxPlayers());
        tournamentToUpdate.setFormat(request.format());
        tournamentToUpdate.setPrice(request.price());
        tournamentToUpdate.setOpen(request.open());
        tournamentRepository.save(tournamentToUpdate);

        return new SingleTournamentResponse("Tournament updated", tournamentToUpdate );
    }

    @Override
    public GeneralResponse deleteTournament(User user, UUID tournamentId) {
        Tournament tournamentToDelete = tournamentRepository.findById(tournamentId)
                .orElseThrow(()-> new IllegalArgumentException("tournament not found"));
        tournamentRepository.delete(tournamentToDelete);
        return new GeneralResponse("Tournament deleted");
    }
    @Override
    public TournamentsResponse getAllTournaments(UUID userId){
        return new TournamentsResponse("Tournaments history: ", tournamentRepository.findByOrganizerId(userId));
    }

    public Tournament getTournamentById(UUID tournamentId) {
        return tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new IllegalArgumentException("tournament not found."));
    }

    public void updatePrizeMoneyAndSpotsAvailble(UUID tournamentid, int totalPlayers){
        Tournament tournament = getTournamentById(tournamentid);
        tournament.setPrizeMoney(tournament.getPrice().multiply(new BigDecimal(totalPlayers)));
        if (tournament.getMaxPlayers() == totalPlayers){
            tournament.setFull(true);
        }
        tournamentRepository.save(tournament);
    }


}
