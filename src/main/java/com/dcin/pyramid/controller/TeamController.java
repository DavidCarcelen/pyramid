package com.dcin.pyramid.controller;

import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.dto.TeamRequest;
import com.dcin.pyramid.model.entity.User;
import com.dcin.pyramid.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")
public class TeamController {
    private final TeamService teamService;

    @PostMapping
    public ResponseEntity<GeneralResponse> createTeam(@AuthenticationPrincipal User user, @RequestBody TeamRequest teamRequest){
        return ResponseEntity.ok(teamService.createTeam(user, teamRequest));
    }
    @PutMapping
    public ResponseEntity<GeneralResponse>updateTeam(@AuthenticationPrincipal User user,@RequestBody TeamRequest teamRequest){
        return ResponseEntity.ok(teamService.updateTeam(user, teamRequest));
    }
    @DeleteMapping
    public ResponseEntity<GeneralResponse>deleteTeam(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(teamService.deleteTeam(user));
    }
    @PatchMapping("/{userId}/add-member")
    public ResponseEntity<GeneralResponse>addTeamMember(@AuthenticationPrincipal User user, @PathVariable UUID userId){
        return ResponseEntity.ok(teamService.addMember(user, userId));
    }
    @PatchMapping("/{userId}/remove-member")
    public ResponseEntity<GeneralResponse>removeTeamMember(@AuthenticationPrincipal User user,@PathVariable UUID userId){
        return ResponseEntity.ok(teamService.removeMember(user, userId));
    }
}
