package com.dcin.pyramid.service;

import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.dto.TeamRequest;
import com.dcin.pyramid.model.entity.Player;
import com.dcin.pyramid.model.entity.User;

import java.util.UUID;

public interface TeamService {
    GeneralResponse createTeam(Player player, TeamRequest teamRequest);
    GeneralResponse updateTeam(Player player, TeamRequest teamRequest);
    GeneralResponse deleteTeam(Player player);
    GeneralResponse addMember(Player player, UUID playerId);
    GeneralResponse removeMember(Player player, UUID playerId);


}
