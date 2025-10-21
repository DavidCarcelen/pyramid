package com.dcin.pyramid.model.dto;

import com.dcin.pyramid.model.entity.Tournament;

import java.util.List;

public record TournamentsResponse(String message, List<TournamentDTO> tournaments) {
}
