package com.dcin.pyramid.model.dto;

import com.dcin.pyramid.model.entity.Role;
import com.dcin.pyramid.model.entity.Team;

import java.util.UUID;

public record UserDTO(UUID id, String email, String nickname, Role role, String team, String profilePictureUrl) {
}
