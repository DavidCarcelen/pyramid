package com.dcin.pyramid.service;

import com.dcin.pyramid.model.dto.NewTournamentRequest;
import com.dcin.pyramid.model.dto.TournamentCreationResponse;
import com.dcin.pyramid.model.entity.Tournament;
import com.dcin.pyramid.model.entity.User;

import java.util.List;

public interface PyramidService {
    TournamentCreationResponse createTournament(NewTournamentRequest request, User user);

    List<Tournament> getUpcommingTournamentsByOrganizer(User organizer);

}
