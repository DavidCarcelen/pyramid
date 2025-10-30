package com.dcin.pyramid.model.dto.auth;

import com.dcin.pyramid.model.dto.Role;
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