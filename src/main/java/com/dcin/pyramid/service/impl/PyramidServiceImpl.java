package com.dcin.pyramid.service.impl;

import com.dcin.pyramid.exception.UserNotFoundException;
import com.dcin.pyramid.model.dto.NewTournamentRequest;
import com.dcin.pyramid.model.dto.TournamentManagementResponse;
import com.dcin.pyramid.model.entity.Tournament;
import com.dcin.pyramid.model.entity.User;
import com.dcin.pyramid.repository.TournamentRepository;
import com.dcin.pyramid.repository.UserRepository;
import com.dcin.pyramid.service.PyramidService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
@RequiredArgsConstructor
public class PyramidServiceImpl implements PyramidService {

    private final TournamentRepository tournamentRepository;
    private final UserRepository userRepository;
    @Transactional
    @Override
    public TournamentManagementResponse createTournament(NewTournamentRequest request, User user) {
        if(request.date().isBefore(LocalDate.now())){
            throw new IllegalArgumentException("Tournament date cannot be in the past.");
        }
        Tournament tournament = Tournament.builder()
                .tournamentName(request.tournamentName())
                .date(request.date())
                .maxPlayers(request.maxPlayers())
                .format(request.format())
                .price(request.price())
                .organizer(user)
                .build();
        tournamentRepository.save(tournament);
        String message = "Tournament successfully created!, this are your next tournaments: ";
        return getTournamentsByOrganizer(user, LocalDate.now(), message);
    }

    @Override
    public TournamentManagementResponse getTournamentsByOrganizer(User organizer, LocalDate date, String message) {
        List<Tournament> tournaments = tournamentRepository.findByOrganizerAndDateAfter(organizer, date);
        return new TournamentManagementResponse(message, tournaments);
    }
    @Override
    public User checkUserName(String name){
        return userRepository.findByNickname(name).orElseThrow(()-> new UsernameNotFoundException("No user found with the given name."));
    }

    @Override
    public TournamentManagementResponse getAllUpcomingTournaments() {
        List<Tournament> tournaments = tournamentRepository.findAllAndDateAfter(LocalDate.now());
        return new TournamentManagementResponse("Upcoming Tournaments: ", tournaments);
    }

}
