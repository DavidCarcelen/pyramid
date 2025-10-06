package com.dcin.pyramid.service.impl;

import com.dcin.pyramid.model.dto.NewTournamentRequest;
import com.dcin.pyramid.model.dto.TournamentResponse;
import com.dcin.pyramid.model.entity.Tournament;
import com.dcin.pyramid.model.entity.User;
import com.dcin.pyramid.repository.TournamentRepository;
import com.dcin.pyramid.repository.UserRepository;
import com.dcin.pyramid.service.TournamentService;
import com.dcin.pyramid.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService {

    private final TournamentRepository tournamentRepository;
    private final UserService userService;

    @Transactional
    @Override
    public TournamentResponse createTournament(NewTournamentRequest request, User user) {
        tournamentDateChecker(request.date());
        Tournament tournament = Tournament.builder()
                .tournamentName(request.tournamentName())
                .date(request.date())
                .maxPlayers(request.maxPlayers())
                .format(request.format())
                .price(request.price())
                .organizer(user)
                .build();
        tournamentRepository.save(tournament);
        return getUpcomingTournamentsByStore(user.getId());
    }

    @Override
    public TournamentResponse getUpcomingTournamentsByStore(UUID storeId) {
        List<Tournament> tournaments;
        if (storeId != null) {
            userService.checkId(storeId);
            tournaments = tournamentRepository.findByOrganizerIdAndDateAfter(storeId, LocalDate.now());
        } else {
            tournaments = tournamentRepository.findAllByDateAfter(LocalDate.now());
        }
        return new TournamentResponse("Upcoming tournaments: ", tournaments);
    }

    @Override
    public void tournamentDateChecker(LocalDate date) {
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Tournament date cannot be in the past.");
        }
    }


}
