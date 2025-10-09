package com.dcin.pyramid.model.dto;

import com.dcin.pyramid.model.entity.Tournament;

public record SingleTournamentResponse(String message, Tournament tournament) {
}
