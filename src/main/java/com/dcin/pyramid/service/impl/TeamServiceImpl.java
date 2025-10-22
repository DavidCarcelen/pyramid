package com.dcin.pyramid.service.impl;

import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.entity.User;
import com.dcin.pyramid.service.TeamService;

import java.util.UUID;

public class TeamServiceImpl implements TeamService {
    @Override
    public GeneralResponse createTeam(User founder, String teamName) {
    return null;
    }

    @Override
    public GeneralResponse addMember(User user, UUID newMemberId) {
        return null;
    }

    @Override
    public GeneralResponse deleteTeam(User user) {
        return null;
    }

    @Override
    public GeneralResponse updateTeam(User user, String newTeamName) {
        return null;
    }
}
