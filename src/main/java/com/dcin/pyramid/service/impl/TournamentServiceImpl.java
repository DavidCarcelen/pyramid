package com.dcin.pyramid.service.impl;

import com.dcin.pyramid.model.dto.TournamentRequest;
import com.dcin.pyramid.model.dto.TournamentResponse;
import com.dcin.pyramid.model.entity.Tournament;
import com.dcin.pyramid.model.entity.User;
import com.dcin.pyramid.repository.TournamentRepository;
import com.dcin.pyramid.service.TournamentService;
import com.dcin.pyramid.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public TournamentResponse createTournament(TournamentRequest request, User user) {
        tournamentDateChecker(request.startDateTime());
        Tournament tournament = Tournament.builder()
                .tournamentName(request.tournamentName())
                .startDateTime(request.startDateTime())
                .maxPlayers(request.maxPlayers())
                .format(request.format())
                .price(request.price())
                .organizer(user)
                .open(request.open())
                .build();
        tournamentRepository.save(tournament);
        return getUpcomingTournamentsByStore(user.getId());
    }

    @Override
    public TournamentResponse getUpcomingTournamentsByStore(UUID storeId) {
        List<Tournament> tournaments;
        if (storeId != null) {
            userService.checkId(storeId);
            tournaments = tournamentRepository.findByOrganizerIdAndStartDateTimeAfter(storeId, LocalDate.now());
        } else {
            tournaments = tournamentRepository.findAllByStartDateTimeAfter(LocalDateTime.now());
        }
        return new TournamentResponse("Upcoming tournaments: ", tournaments);
    }

    @Override
    public void tournamentDateChecker(LocalDateTime date) {
        if (date.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Tournament date cannot be in the past.");
        }
    }

    @Override
    public TournamentResponse updateTournament(TournamentRequest request, User user) {///check how to get tournament and check user to organizer
        tournamentDateChecker(request.startDateTime());
        return null;
    }

    @Override
    public TournamentResponse deleteTournament(User user) {
        return null;
    }


}
