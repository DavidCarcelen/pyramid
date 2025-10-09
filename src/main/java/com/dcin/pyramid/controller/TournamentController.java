package com.dcin.pyramid.controller;

import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.dto.SingleTournamentResponse;
import com.dcin.pyramid.model.dto.TournamentRequest;
import com.dcin.pyramid.model.dto.TournamentsResponse;
import com.dcin.pyramid.model.entity.User;
import com.dcin.pyramid.service.TournamentService;
import jakarta.validation.Valid;
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
    public ResponseEntity<SingleTournamentResponse> createTournament(@Valid @RequestBody TournamentRequest request,
                                                               @AuthenticationPrincipal User user){
        return ResponseEntity.ok(tournamentService.createTournament(request, user));
    }

    @GetMapping("/myTournaments")
    public ResponseEntity<TournamentsResponse> getAllMyTournaments(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(tournamentService.getAllTournaments(user.getId()));
    }

    @GetMapping("/upcomingTournaments")
    public ResponseEntity<TournamentsResponse> getUpcomingTournamentsByStore(@RequestParam(required = false) UUID storeId){

        return ResponseEntity.ok(tournamentService.getUpcomingTournamentsByStore(storeId));
    }

    @PostMapping("/update")
    public ResponseEntity<SingleTournamentResponse>updateTournament(@AuthenticationPrincipal User user,
                                                                    @Valid @RequestBody TournamentRequest tournamentRequest,
                                                                    @RequestParam(required = true) UUID tournamentId){
        return ResponseEntity.ok(tournamentService.updateTournament(tournamentRequest, user, tournamentId));
    }
    @DeleteMapping("/{tournamentId}")
    public ResponseEntity<GeneralResponse>deleteTournament(@AuthenticationPrincipal User user,
                                                           @PathVariable UUID tournamentId){
        return ResponseEntity.ok(tournamentService.deleteTournament(user,tournamentId));
    }

}
