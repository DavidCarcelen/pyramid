package com.dcin.pyramid.controller.store;

import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.dto.tournament.SingleTournamentResponse;
import com.dcin.pyramid.model.dto.tournament.TournamentRequest;
import com.dcin.pyramid.model.dto.tournament.TournamentsResponse;
import com.dcin.pyramid.model.entity.Store;
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
@RequestMapping("/pyramid/store/tournaments")
@RequiredArgsConstructor
@PreAuthorize("hasRole('STORE')")
public class TournamentController {
    private final TournamentService tournamentService;
    private final TournamentRegistrationCoordinator tournamentRegistrationCoordinator;

    @PostMapping("/new")
    public ResponseEntity<SingleTournamentResponse> createTournament(@AuthenticationPrincipal Store store,
                                                                     @Valid @RequestBody TournamentRequest request) {
        return ResponseEntity.ok(tournamentService.createTournament(store, request));
    }

    @GetMapping("/my-tournaments")
    public ResponseEntity<TournamentsResponse> getAllMyTournaments(@AuthenticationPrincipal Store store) {
        return ResponseEntity.ok(tournamentService.getAllTournaments(store.getId()));
    }


    @PutMapping("/update/{tournamentId}")
    public ResponseEntity<SingleTournamentResponse> updateTournament(@AuthenticationPrincipal Store store,
                                                                     @PathVariable UUID tournamentId,
                                                                     @Valid @RequestBody TournamentRequest tournamentRequest) {
        return ResponseEntity.ok(tournamentService.updateTournament(store, tournamentRequest, tournamentId));
    }


    @DeleteMapping("/{tournamentId}")
    public ResponseEntity<GeneralResponse> deleteTournament(@AuthenticationPrincipal Store store,
                                                            @PathVariable UUID tournamentId) {
        return ResponseEntity.ok(tournamentService.deleteTournament(store, tournamentId));
    }


    @PatchMapping("/state/{tournamentId}")
    public ResponseEntity<GeneralResponse> setTournamentState(@AuthenticationPrincipal Store store,
                                                              @PathVariable UUID tournamentId,
                                                              @RequestParam boolean openTournament) {
        return ResponseEntity.ok(tournamentService.openCloseTournament(store, tournamentId, openTournament));
    }


    @PatchMapping("/code/{tournamentId}")
    public ResponseEntity<GeneralResponse> setTournamentCompanionCode(@AuthenticationPrincipal Store store,
                                                                      @PathVariable UUID tournamentId,
                                                                      @RequestParam String companionCode) {
        return ResponseEntity.ok(tournamentService.addCompanionCode(store, tournamentId, companionCode));
    }


    @PatchMapping("/update-max-players/{tournamentId}")
    public ResponseEntity<GeneralResponse> updateMaxPlayers(@AuthenticationPrincipal Store store,
                                                            @PathVariable UUID tournamentId,
                                                            @RequestParam int newMaxPlayers) {
        return ResponseEntity.ok(tournamentRegistrationCoordinator.updateMaxPlayers(store, tournamentId, newMaxPlayers));
    }


    @PatchMapping("/finish-tournament/{tournamentId}")
    public ResponseEntity<GeneralResponse> finishTournament(@AuthenticationPrincipal Store store,
                                                            @PathVariable UUID tournamentId) {
        return ResponseEntity.ok(tournamentService.finishTournament(store, tournamentId));
    }


}
