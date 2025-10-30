package com.dcin.pyramid.model.dto.tournament;

import com.dcin.pyramid.model.dto.tournament.TournamentInfoDTO;

import java.util.List;

public record TournamentsResponse(String message, List<TournamentInfoDTO> tournaments) {
}
