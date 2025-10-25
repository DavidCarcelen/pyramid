package com.dcin.pyramid.service;

import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.dto.TeamRequest;
import com.dcin.pyramid.model.entity.User;

import java.util.UUID;

public interface TeamService {
    GeneralResponse createTeam(User user, TeamRequest teamRequest);
    GeneralResponse addMember(User user, UUID newMemberId);
    GeneralResponse removeMember(User user, UUID newMemberId);
    GeneralResponse deleteTeam(User user);
    GeneralResponse updateTeam(User user, TeamRequest teamRequest);
}
