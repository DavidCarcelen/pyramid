package com.dcin.pyramid.model.dto;

import com.dcin.pyramid.model.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignUpRequest(
        @NotBlank(message = "Email required")
        String email,
        @NotBlank(message = "Password required")
        String password,
        @NotBlank(message = "Nickname required")
        String nickname,
        @NotNull(message = "Role is required")
        Role role) {
}