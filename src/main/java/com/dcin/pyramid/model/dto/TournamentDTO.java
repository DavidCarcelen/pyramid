package com.dcin.pyramid.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TournamentDTO (
        UUID id,
        String tournamentName,
        LocalDateTime startDateTime,
        int maxPlayers,
        String format,
        String extraInfo,
        BigDecimal price,
        boolean openTournament,
        boolean fullTournament,
        String organizerNickname){
}
