package com.dcin.pyramid.service.impl;

import com.dcin.pyramid.exception.InvalidTeamOperationException;
import com.dcin.pyramid.exception.UnauthorizedActionException;
import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.dto.TeamRequest;
import com.dcin.pyramid.model.entity.Team;
import com.dcin.pyramid.model.entity.User;
import com.dcin.pyramid.repository.TeamRepository;
import com.dcin.pyramid.repository.UserRepository;
import com.dcin.pyramid.service.TeamService;
import com.dcin.pyramid.service.UserService;
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
    private final UserService userService;
    private final UserRepository userRepository;
    @Override
    public GeneralResponse createTeam(User user, TeamRequest teamRequest) {
        teamUtils.checkIfUserIsNotInATeam(user);
        checkTeamNameAvailable(teamRequest.teamName());
        checkTeamEmojiAvailable(teamRequest.teamEmoji());
        Team team = Team.builder()
                .teamName(teamRequest.teamName())
                .teamEmoji(teamRequest.teamEmoji())
                .founder(user)
                .createdAt(LocalDateTime.now())
                .build();
        teamRepository.save(team);
        user.setTeam(team);
        userRepository.save(user);

        return new GeneralResponse("Team " + team.getTeamName() + " successfully created.");
    }

    @Override
    public GeneralResponse addMember(User user, UUID newMemberId) {
        teamUtils.checkUserFounder(user);
        User newMember = userService.getUserById(newMemberId);
        teamUtils.checkIfUserIsNotInATeam(newMember);
        newMember.setTeam(user.getTeam());
        userRepository.save(newMember);
        return new GeneralResponse(newMember.getNickname() + " added to team " + newMember.getTeam().getTeamName());
    }

    @Override
    public GeneralResponse removeMember(User user, UUID memberToRemoveId) {
        teamUtils.checkUserFounder(user);
        Team team = user.getTeam();
        User memberToRemove = userService.getUserById(memberToRemoveId);
        teamUtils.checkIfUserIsMember(memberToRemove, team);
        memberToRemove.setTeam(null);
        userRepository.save(memberToRemove);
        return new GeneralResponse(memberToRemove.getNickname() + " removed from team " + team.getTeamName());
    }

    @Override
    public GeneralResponse deleteTeam(User user) {
        teamUtils.checkUserFounder(user);
        Team team = user.getTeam();
        List<User> members = userRepository.findByTeamId(team.getId());
        members.forEach(member -> member.setTeam(null));
        userRepository.saveAll(members);
        teamRepository.delete(team);
        return new GeneralResponse("Team " + team.getTeamName() + " deleted.");
    }

    @Override
    public GeneralResponse updateTeam(User user, TeamRequest teamRequest) {
        teamUtils.checkUserFounder(user);
        Team teamToUpdate = user.getTeam();
        if(!teamToUpdate.getTeamName().equals(teamRequest.teamName())){
            checkTeamNameAvailable(teamRequest.teamName());
        }
        if (teamToUpdate.getTeamEmoji().equals(teamRequest.teamEmoji())){
            checkTeamEmojiAvailable(teamRequest.teamEmoji());
        }
        teamToUpdate.setTeamName(teamRequest.teamName());
        teamToUpdate.setTeamEmoji(teamRequest.teamEmoji());
        teamRepository.save(teamToUpdate);
        return new GeneralResponse("Team " + teamToUpdate.getTeamName() + " updated.");
    }


    public void checkTeamNameAvailable(String teamName){
       if(teamRepository.existsByTeamName(teamName)){
           throw new InvalidTeamOperationException("Team name not available.");
       }
    }
    public void checkTeamEmojiAvailable(String teamEmoji){
        if(teamRepository.existsByTeamEmoji(teamEmoji)){
            throw new InvalidTeamOperationException("Team emoji not available.");
        }
    }

}
