package com.dcin.pyramid.controller;

import com.dcin.pyramid.model.dto.TournamentRequest;
import com.dcin.pyramid.model.dto.TournamentResponse;
import com.dcin.pyramid.model.entity.User;
import com.dcin.pyramid.service.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/tournaments")
@RequiredArgsConstructor
public class TournamentController {
    private final TournamentService tournamentService;

    @PostMapping("/new")
    public ResponseEntity<TournamentResponse> createTournament(@RequestBody TournamentRequest request,
                                                               @AuthenticationPrincipal User user){
        //userService.checkUserRole(user, Role.STORE);
        return ResponseEntity.ok(tournamentService.createTournament(request, user));
    }

    @GetMapping("/myTournaments")
    public ResponseEntity<TournamentResponse> getAllMyTournaments(@AuthenticationPrincipal User user){//not upcoming
        //userService.checkUserRole(user, Role.STORE);
        return ResponseEntity.ok(tournamentService.getUpcomingTournamentsByStore(user.getId()));
    }

    @GetMapping("/upcomingTournaments")
    public ResponseEntity<TournamentResponse> getUpcomingTournamentsByStore(@RequestParam(required = false) UUID storeId){//make flexible different stores or favourites

        return ResponseEntity.ok(tournamentService.getUpcomingTournamentsByStore(storeId));
    }

}
