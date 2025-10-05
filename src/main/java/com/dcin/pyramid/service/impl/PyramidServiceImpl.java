package com.dcin.pyramid.service.impl;

import com.dcin.pyramid.model.dto.NewTournamentRequest;
import com.dcin.pyramid.model.dto.TournamentCreationResponse;
import com.dcin.pyramid.model.entity.Tournament;
import com.dcin.pyramid.model.entity.User;
import com.dcin.pyramid.repository.TournamentRepository;
import com.dcin.pyramid.service.PyramidService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
@RequiredArgsConstructor
public class PyramidServiceImpl implements PyramidService {

    private final TournamentRepository tournamentRepository;
    @Override
    public TournamentCreationResponse createTournament(NewTournamentRequest request, User user) {
        Tournament tournament = Tournament.builder()
                .tournamentName(request.tournamentName())
                .date(request.date())
                .maxPlayers(request.maxPlayers())
                .format(request.format())
                .price(request.price())
                .organizer(user)
                .build();
        String message = "Tournament successfully created!";
        List<Tournament> storeTournaments = getUpcommingTournamentsByOrganizer(user);
        return new TournamentCreationResponse(message,storeTournaments);
    }

    @Override
    public List<Tournament> getUpcommingTournamentsByOrganizer(User organizer) {
        return tournamentRepository.findByOrganizerAndDateAfter(organizer, LocalDate.now());
    }
}
