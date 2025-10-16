package com.dcin.pyramid.model.dto;

import java.time.LocalDateTime;

public record RegistrationInfoDTO(String nickname, boolean reserveList, LocalDateTime registeredAt) {
}
