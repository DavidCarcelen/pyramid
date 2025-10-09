package com.dcin.pyramid.model.dto;

import java.util.List;

public record RegistrationsResponse(List<String> players, int totalPlayers) {
}
