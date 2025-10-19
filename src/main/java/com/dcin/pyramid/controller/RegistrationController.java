package com.dcin.pyramid.controller;

import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.dto.RegistrationsResponse;
import com.dcin.pyramid.model.entity.User;
import com.dcin.pyramid.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/registrations")
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping("/{tournamentId}")
    public ResponseEntity<RegistrationsResponse> newRegistration(@AuthenticationPrincipal User player,
                                                                 @PathVariable UUID tournamentId) {
        return ResponseEntity.ok(registrationService.handleRegistration(player, tournamentId));
    }

    @DeleteMapping("/{registrationId}")
    public ResponseEntity<GeneralResponse> deleteRegistration(@AuthenticationPrincipal User player,
                                                              @PathVariable UUID registrationId) {
        return ResponseEntity.ok(registrationService.deleteRegistration(player, registrationId));
    }

    @GetMapping("/{tournamentId}")
    public ResponseEntity<RegistrationsResponse> getPlayersForOneTournament(@PathVariable UUID tournamentId) {
        return ResponseEntity.ok(registrationService.getAllRegistrations(tournamentId));
    }

    @PreAuthorize("hasRole('STORE')")
    @PatchMapping("/{registrationId}/mark-paid")
    public ResponseEntity<GeneralResponse> markRegistrationAsPaid(@AuthenticationPrincipal User store,
                                                                  @PathVariable UUID registrationId) {
        return ResponseEntity.ok(registrationService.markAsPaid(store, registrationId));
    }

}
