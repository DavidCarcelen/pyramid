package com.dcin.pyramid.model.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record StoreSignUpRequest(
        @NotBlank(message = "Email required")
        String email,
        @NotBlank(message = "Password required")
        String password,
        @NotBlank(message = "Store name is required")
        String nickname,
        @NotBlank(message = "Address is required")
        String address) {
}
