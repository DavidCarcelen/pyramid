package com.dcin.pyramid.model.dto.registration;

import java.util.List;

public record RegistrationsResponse(String message, List<RegistrationInfoDTO> players, int totalPlayers, int reservePlayers, boolean fullTournament) {
}
