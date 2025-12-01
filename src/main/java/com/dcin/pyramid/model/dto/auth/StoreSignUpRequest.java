package com.dcin.pyramid.model.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record StoreSignUpRequest(
        @NotBlank(message = "Email required")
        String email,
        @NotBlank(message = "Password required")
        String password,
        @NotBlank(message = "Store name is required")
        String nickname,
        @NotBlank(message = "Country is required")
        String country,
        @NotBlank(message = "City is required")
        String city,
        String googleMapsLink,
        @NotBlank(message = "Store capacity is required")
        int storeCapacity,
        String cardMarketLink){

}




