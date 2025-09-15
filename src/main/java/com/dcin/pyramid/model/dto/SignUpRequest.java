package com.dcin.pyramid.model.dto;

import com.dcin.pyramid.model.entity.Role;

public record SignUpRequest(String email, String password, String nickname, Role role) {
}