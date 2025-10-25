package com.dcin.pyramid.util;

import com.dcin.pyramid.exception.InvalidTeamOperationException;
import com.dcin.pyramid.exception.UnauthorizedActionException;
import com.dcin.pyramid.model.entity.Team;
import com.dcin.pyramid.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class TeamUtils {
    public void checkUserFounder(User user) {
       Team team = user.getTeam();
        if (team == null || !team.getFounder().equals(user)) {
            throw new UnauthorizedActionException("Only the founder can operate.");
        }
    }

    public void checkIfUserIsNotInATeam(User user) {
        if (user.getTeam() != null) {
            throw new InvalidTeamOperationException("User on a team already.");
        }

    }

    public void checkIfUserIsMember(User user, Team team) {
        if (user.getTeam().equals(team)) {
            throw new InvalidTeamOperationException("User is not member of the team.");
        }

    }
}
