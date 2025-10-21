package com.dcin.pyramid.model.mappers;

import com.dcin.pyramid.model.dto.TournamentDTO;
import com.dcin.pyramid.model.entity.Tournament;
import org.springframework.stereotype.Component;

@Component
public class TournamentMapper {
    public TournamentDTO toDTO (Tournament tournament){
        return new TournamentDTO(
                tournament.getId(),
                tournament.getTournamentName(),
                tournament.getStartDateTime(),
                tournament.getMaxPlayers(),
                tournament.getFormat(),
                tournament.getExtraInfo(),
                tournament.getPrice(),
                tournament.isOpenTournament(),
                tournament.isFullTournament(),
                tournament.getOrganizer().getNickname()
        );
    }
}
