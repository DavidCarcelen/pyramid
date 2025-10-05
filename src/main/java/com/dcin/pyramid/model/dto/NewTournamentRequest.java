package com.dcin.pyramid.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record NewTournamentRequest (String tournamentName, LocalDate date, int maxPlayers, String format, BigDecimal price){
}
