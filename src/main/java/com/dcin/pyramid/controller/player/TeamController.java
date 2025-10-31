package com.dcin.pyramid.controller.player;

import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.dto.TeamRequest;
import com.dcin.pyramid.model.entity.Player;
import com.dcin.pyramid.model.entity.User;
import com.dcin.pyramid.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pyramid/players/teams")
@PreAuthorize("hasRole('PLAYER')")
public class TeamController {
    private final TeamService teamService;

    @PostMapping
    public ResponseEntity<GeneralResponse> createTeam(@AuthenticationPrincipal Player player, @RequestBody TeamRequest teamRequest){
        return ResponseEntity.ok(teamService.createTeam(player, teamRequest));
    }
    @PutMapping
    public ResponseEntity<GeneralResponse>updateTeam(@AuthenticationPrincipal Player player,@RequestBody TeamRequest teamRequest){
        return ResponseEntity.ok(teamService.updateTeam(player, teamRequest));
    }
    @DeleteMapping
    public ResponseEntity<GeneralResponse>deleteTeam(@AuthenticationPrincipal Player player){
        return ResponseEntity.ok(teamService.deleteTeam(player));
    }
    @PatchMapping("/{playerId}/add-member")
    public ResponseEntity<GeneralResponse>addTeamMember(@AuthenticationPrincipal Player player, @PathVariable UUID playerId){
        return ResponseEntity.ok(teamService.addMember(player, playerId));
    }
    @PatchMapping("/{playerId}/remove-member")
    public ResponseEntity<GeneralResponse>removeTeamMember(@AuthenticationPrincipal Player player,@PathVariable UUID playerId){
        return ResponseEntity.ok(teamService.removeMember(player, playerId));
    }
}
