package com.dcin.pyramid.service.impl;

import com.dcin.pyramid.exception.InvalidTeamOperationException;
import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.dto.TeamRequest;
import com.dcin.pyramid.model.entity.Player;
import com.dcin.pyramid.model.entity.Team;
import com.dcin.pyramid.repository.PlayerRepository;
import com.dcin.pyramid.repository.TeamRepository;
import com.dcin.pyramid.service.PlayerService;
import com.dcin.pyramid.service.TeamService;
import com.dcin.pyramid.util.TeamUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final TeamUtils teamUtils;
    private final PlayerService playerService;
    private final PlayerRepository playerRepository;

    @Override
    public GeneralResponse createTeam(Player player, TeamRequest teamRequest) {
        teamUtils.checkIfPlayerIsNotInATeam(player);
        checkTeamNameAvailable(teamRequest.teamName());
        checkTeamEmojiAvailable(teamRequest.teamEmoji());
        Team team = Team.builder()
                .teamName(teamRequest.teamName())
                .teamEmoji(teamRequest.teamEmoji())
                .founder(player)
                .createdAt(LocalDateTime.now())
                .build();
        teamRepository.save(team);
        player.setTeam(team);
        playerRepository.save(player);

        return new GeneralResponse("Team " + team.getTeamName() + " successfully created.");
    }

    @Override
    public GeneralResponse updateTeam(Player player, TeamRequest teamRequest) {
        teamUtils.checkPlayerFounder(player);
        Team teamToUpdate = player.getTeam();
        if (!teamToUpdate.getTeamName().equals(teamRequest.teamName())) {
            checkTeamNameAvailable(teamRequest.teamName());
        }
        if (teamToUpdate.getTeamEmoji().equals(teamRequest.teamEmoji())) {
            checkTeamEmojiAvailable(teamRequest.teamEmoji());
        }
        teamToUpdate.setTeamName(teamRequest.teamName());
        teamToUpdate.setTeamEmoji(teamRequest.teamEmoji());
        teamRepository.save(teamToUpdate);
        return new GeneralResponse("Team " + teamToUpdate.getTeamName() + " updated.");
    }

    @Override
    public GeneralResponse deleteTeam(Player player) {
        teamUtils.checkPlayerFounder(player);
        Team team = player.getTeam();
        List<Player> members = playerRepository.findByTeamId(team.getId());
        members.forEach(member -> member.setTeam(null));
        playerRepository.saveAll(members);
        teamRepository.delete(team);
        return new GeneralResponse("Team " + team.getTeamName() + " deleted.");
    }

    @Override
    public GeneralResponse addMember(Player player, UUID playerId) {
        teamUtils.checkPlayerFounder(player);
        Player newMember = playerService.getPlayerById(playerId);
        teamUtils.checkIfPlayerIsNotInATeam(newMember);
        newMember.setTeam(player.getTeam());
        playerRepository.save(newMember);
        return new GeneralResponse(newMember.getNickname() + " added to team " + newMember.getTeam().getTeamName());
    }

    @Override
    public GeneralResponse removeMember(Player player, UUID playerId) {
        teamUtils.checkPlayerFounder(player);
        Team team = player.getTeam();
        Player memberToRemove = playerService.getPlayerById(playerId);
        teamUtils.checkIfPlayerIsMember(memberToRemove, team); //simplify this get player from team?
        memberToRemove.setTeam(null);
        playerRepository.save(memberToRemove);
        return new GeneralResponse(memberToRemove.getNickname() + " removed from team " + team.getTeamName());
    }


    public void checkTeamNameAvailable(String teamName) {
        if (teamRepository.existsByTeamName(teamName)) {
            throw new InvalidTeamOperationException("Team name not available.");
        }
    }

    public void checkTeamEmojiAvailable(String teamEmoji) {
        if (teamRepository.existsByTeamEmoji(teamEmoji)) {
            throw new InvalidTeamOperationException("Team emoji not available.");
        }
    }

}
