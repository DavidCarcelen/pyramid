package com.dcin.pyramid.model.mappers;

import com.dcin.pyramid.model.dto.tournament.TournamentInfoDTO;
import com.dcin.pyramid.model.entity.Tournament;
import org.springframework.stereotype.Component;

@Component
public class TournamentMapper {
    public TournamentInfoDTO toDTO (Tournament tournament){
        return new TournamentInfoDTO(
                tournament.getId(),
                tournament.getTournamentName(),
                tournament.getStartDateTime(),
                tournament.getMaxPlayers(),
                tournament.getFormat(),
                tournament.getExtraInfo(),
                tournament.getPrice(),
                tournament.getOrganizer().getNickname(),
                tournament.getCompanionCode(),
                tournament.isOpenTournament(),
                tournament.isFullTournament(),
                tournament.isFinished()
        );
    }
}
