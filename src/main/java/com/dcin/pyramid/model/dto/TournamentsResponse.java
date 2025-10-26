package com.dcin.pyramid.model.dto;

import java.util.List;

public record TournamentsResponse(String message, List<TournamentInfoDTO> tournaments) {
}
