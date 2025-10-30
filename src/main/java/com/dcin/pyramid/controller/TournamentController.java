package com.dcin.pyramid.controller;

import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.dto.tournament.SingleTournamentResponse;
import com.dcin.pyramid.model.dto.tournament.TournamentRequest;
import com.dcin.pyramid.model.dto.tournament.TournamentsResponse;
import com.dcin.pyramid.model.entity.User;
import com.dcin.pyramid.service.TournamentService;
import com.dcin.pyramid.service.impl.TournamentRegistrationCoordinator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/pyramid/tournaments")
@RequiredArgsConstructor
public class TournamentController {
    private final TournamentService tournamentService;
    private final TournamentRegistrationCoordinator tournamentRegistrationCoordinator;

    @PreAuthorize("hasRole('STORE')")
    @PostMapping("/store/new")
    public ResponseEntity<SingleTournamentResponse> createTournament(@AuthenticationPrincipal User user,
                                                                     @Valid @RequestBody TournamentRequest request) {
        return ResponseEntity.ok(tournamentService.createTournament(request, user));
    }

    @PreAuthorize("hasRole('STORE')")
    @GetMapping("/store/my-tournaments")
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
    @PatchMapping("/store/state/{tournamentId}")
    public ResponseEntity<GeneralResponse> setTournamentState(@AuthenticationPrincipal User user,
                                                              @PathVariable UUID tournamentId,
                                                              @RequestParam boolean openTournament) {
        return ResponseEntity.ok(tournamentService.openCloseTournament(user, tournamentId, openTournament));
    }

    @PreAuthorize("hasRole('STORE')")
    @PatchMapping("/store/code/{tournamentId}")
    public ResponseEntity<GeneralResponse> setTournamentCompanionCode(@AuthenticationPrincipal User user,
                                                                      @PathVariable UUID tournamentId,
                                                                      @RequestParam String companionCode) {
        return ResponseEntity.ok(tournamentService.addCompanionCode(user, tournamentId, companionCode));
    }

    @PreAuthorize("hasRole('STORE')")
    @PatchMapping("/store/update-max-players/{tournamentId}")
    public ResponseEntity<GeneralResponse> updateMaxPlayers(@AuthenticationPrincipal User user,
                                                            @PathVariable UUID tournamentId,
                                                            @RequestParam int newMaxPlayers) {
        return ResponseEntity.ok(tournamentRegistrationCoordinator.updateMaxPlayers(user, tournamentId, newMaxPlayers));
    }

    @PreAuthorize("hasRole('STORE')")
    @PatchMapping("/store/finish-tournament/{tournamentId}")
    public ResponseEntity<GeneralResponse> finishTournament(@AuthenticationPrincipal User user,
                                                            @PathVariable UUID tournamentId) {
        return ResponseEntity.ok(tournamentService.finishTournament(user, tournamentId));
    }

    @GetMapping("/upcoming-tournaments")
    public ResponseEntity<TournamentsResponse> getUpcomingTournamentsByStore(@RequestParam(required = false) UUID storeId) {

        return ResponseEntity.ok(tournamentService.getUpcomingTournamentsByStore(storeId));
    }

    @GetMapping("/get-one-tournament/{tournamentId}")
    public ResponseEntity<SingleTournamentResponse> getOneTournament(@PathVariable UUID tournamentId) {

        return ResponseEntity.ok(tournamentService.getOneTournament(tournamentId));
    }

}
