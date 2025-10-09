package com.dcin.pyramid.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRequest (
        @NotBlank(message = "Email is required")
        String email,
        @NotBlank(message = "Password required")
        String password){
}
