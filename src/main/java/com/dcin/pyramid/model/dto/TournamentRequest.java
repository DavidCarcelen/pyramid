package com.dcin.pyramid.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TournamentRequest(String tournamentName, LocalDateTime startDateTime, int maxPlayers, String format, BigDecimal price, boolean open){
}
