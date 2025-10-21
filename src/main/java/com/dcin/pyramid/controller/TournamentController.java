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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/tournaments")
@RequiredArgsConstructor
public class TournamentController {
    private final TournamentService tournamentService;

    @PreAuthorize("hasRole('STORE')")
    @PostMapping("/store/new")
    public ResponseEntity<SingleTournamentResponse> createTournament(@Valid @RequestBody TournamentRequest request,
                                                                     @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(tournamentService.createTournament(request, user));
    }
    @PreAuthorize("hasRole('STORE')")
    @GetMapping("/store/myTournaments")
    public ResponseEntity<TournamentsResponse> getAllMyTournaments(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(tournamentService.getAllTournaments(user.getId()));
    }
    @PreAuthorize("hasRole('STORE')")
    @PutMapping("/store/update")
    public ResponseEntity<SingleTournamentResponse> updateTournament(@AuthenticationPrincipal User user,
                                                                     @Valid @RequestBody TournamentRequest tournamentRequest,
                                                                     @RequestParam(required = true) UUID tournamentId) {
        return ResponseEntity.ok(tournamentService.updateTournament(tournamentRequest, user, tournamentId));
    }
    @PreAuthorize("hasRole('STORE')")
    @DeleteMapping("/store/{tournamentId}")
    public ResponseEntity<GeneralResponse> deleteTournament(@AuthenticationPrincipal User user,
                                                            @PathVariable UUID tournamentId) {
        return ResponseEntity.ok(tournamentService.deleteTournament(user, tournamentId));
    }
    @PreAuthorize("hasRole('STORE')")
    @PatchMapping("/store/{tournamentId}")
    public ResponseEntity<GeneralResponse> setTournamentState(@AuthenticationPrincipal User user,
                                                          @PathVariable UUID tournamentId,
                                                          @RequestParam boolean open) {
        return ResponseEntity.ok(tournamentService.openCloseTournament(user, tournamentId, open));
    }

    @GetMapping("/upcomingTournaments")
    public ResponseEntity<TournamentsResponse> getUpcomingTournamentsByStore(@RequestParam(required = false) UUID storeId) {

        return ResponseEntity.ok(tournamentService.getUpcomingTournamentsByStore(storeId));
    }

    //getOneTournament

}
