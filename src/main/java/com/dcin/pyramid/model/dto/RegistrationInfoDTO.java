package com.dcin.pyramid.model.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record RegistrationInfoDTO(UUID id, String nickname, boolean reserveList, LocalDateTime registeredAt) {
}
