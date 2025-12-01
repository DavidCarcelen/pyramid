package com.dcin.pyramid.model.dto.user;

import jakarta.validation.constraints.NotBlank;

public record UpdateStoreRequest(String nickname, String country, String city, String googleMapsLink, int storeCapacity, String cardMarketLink) {
}
