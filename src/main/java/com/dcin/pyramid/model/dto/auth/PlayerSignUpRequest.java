package com.dcin.pyramid.model.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record PlayerSignUpRequest(
        @NotBlank(message = "Email required")
        String email,
        @NotBlank(message = "Password required")
        String password,
        @NotBlank(message = "Nickname required")
        String nickname)
      {
}