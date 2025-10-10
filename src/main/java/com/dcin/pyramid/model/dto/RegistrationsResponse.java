package com.dcin.pyramid.model.dto;

import java.util.List;

public record RegistrationsResponse(String message,List<String> players, int totalPlayers) {
}
