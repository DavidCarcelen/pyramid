package com.dcin.pyramid.model.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TournamentRequest(
        @NotBlank(message = "Tournament name is required")
        String tournamentName,
        @NotNull(message = "Start date is required")
        @Future(message = "Tournament date can't be in the past")
        LocalDateTime startDateTime,
        @Positive(message = "Max players must be greater than zero")
        int maxPlayers,
        @NotBlank(message = "Format is required")
        String format,
        String extraInfo,
        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", message = "Price must be zero or positive")
        BigDecimal price,
        @NotNull(message = "Open or closed is required")
        boolean open){
}
