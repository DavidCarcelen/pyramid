package com.dcin.pyramid.model.dto;

import com.dcin.pyramid.model.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignUpRequest(
        @NotBlank(message = "Email required")//check registered
        String email,
        @NotBlank(message = "Password required")
        String password,
        @NotBlank(message = "Nickname required")//check registered
        String nickname,
        @NotNull(message = "Role is required")
        Role role) {
}