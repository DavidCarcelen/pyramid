package com.dcin.pyramid.controller;

import com.dcin.pyramid.model.dto.tournament.SingleTournamentResponse;
import com.dcin.pyramid.model.dto.tournament.TournamentsResponse;
import com.dcin.pyramid.service.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@RestController
@RequiredArgsConstructor
@RequestMapping("/pyramid/feed")
public class FeedController {
    private final TournamentService tournamentService;
    @GetMapping("/upcoming-tournaments")
    public ResponseEntity<TournamentsResponse> getUpcomingTournamentsByStore(@RequestParam(required = false) UUID storeId) {

        return ResponseEntity.ok(tournamentService.getUpcomingTournamentsByStore(storeId));
    }

    @GetMapping("/get-one-tournament/{tournamentId}")
    public ResponseEntity<SingleTournamentResponse> getOneTournament(@PathVariable UUID tournamentId) {//only authenticated, get tournament info, get registered players

        return ResponseEntity.ok(tournamentService.getOneTournament(tournamentId));
    }

}
