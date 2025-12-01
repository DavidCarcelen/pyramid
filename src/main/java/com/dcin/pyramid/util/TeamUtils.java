package com.dcin.pyramid.util;

import com.dcin.pyramid.exception.InvalidTeamOperationException;
import com.dcin.pyramid.exception.UnauthorizedActionException;
import com.dcin.pyramid.model.entity.Player;
import com.dcin.pyramid.model.entity.Team;
import com.dcin.pyramid.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class TeamUtils {
    public void checkPlayerFounder(Player player) {
       Team team = player.getTeam();
        if (team == null || !team.getFounder().equals(player)) {
            throw new UnauthorizedActionException("Only the founder can operate.");
        }
    }

    public void checkIfPlayerIsNotInATeam(Player player) {
        if (player.getTeam() != null) {
            throw new InvalidTeamOperationException("Player on a team already.");
        }

    }

    public void checkIfPlayerIsMember(Player player, Team team) {
        if (!player.getTeam().equals(team)) {
            throw new InvalidTeamOperationException("Player is not member of the team.");
        }

    }
}
