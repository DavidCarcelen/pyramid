package com.dcin.pyramid.service;

import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.entity.User;

import java.util.UUID;

public interface TournamentRegisntartionCoordinator {
    GeneralResponse updateMaxPlayers(User user, UUID tournamentId, int newMaxPlayers);
}
