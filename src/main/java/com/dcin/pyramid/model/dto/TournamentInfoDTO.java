package com.dcin.pyramid.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TournamentInfoDTO(
        UUID id,
        String tournamentName,
        LocalDateTime startDateTime,
        int maxPlayers,
        String format,
        String extraInfo,
        BigDecimal price,
        String organizerNickname,
        String companionCode,
        boolean openTournament,
        boolean fullTournament,
        boolean finished){
}
