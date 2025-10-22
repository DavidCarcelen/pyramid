package com.dcin.pyramid.service;

import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.entity.User;

import java.util.UUID;

public interface TeamService {
    GeneralResponse createTeam(User founder, String teamName);
    GeneralResponse addMember(User user, UUID newMemberId);
    GeneralResponse deleteTeam(User user);
    GeneralResponse updateTeam(User user, String newTeamName);
}
